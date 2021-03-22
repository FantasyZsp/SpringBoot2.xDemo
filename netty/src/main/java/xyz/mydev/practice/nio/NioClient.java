package xyz.mydev.practice.nio;

import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client
 *
 * @author ZSP
 */
public class NioClient {

  private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

  public static void main(String[] args) throws Exception {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    Selector selector = Selector.open();
    socketChannel.register(selector, SelectionKey.OP_CONNECT);
    socketChannel.connect(new InetSocketAddress("127.0.0.1", 5000));

    while (true) {
      int eventCount = selector.select();
      Assert.isTrue(eventCount != 0, "test");
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = selectionKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey selectionKey = iterator.next();
        SocketChannel client = (SocketChannel) selectionKey.channel();

        // 处理连接
        if (selectionKey.isConnectable()) {
          if (client.isConnectionPending()) {
            client.finishConnect();
            ByteBuffer writeBuffer = ByteBuffer.allocate(512);
            writeBuffer.put((LocalDateTime.now() + "连接成功！").getBytes());
            writeBuffer.flip();
            client.write(writeBuffer);
            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
            // 连接建立后，客户端监听键盘输入，向服务端发送数据
            executorService.submit(() -> {
              while (true) {
                try {
                  writeBuffer.clear();
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                  String msg = bufferedReader.readLine();
                  writeBuffer.put(msg.getBytes());
                  writeBuffer.flip();
                  client.write(writeBuffer);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
          }

          // 建立连接后，注册监听服务器发送消息时间
          client.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
          ByteBuffer byteBuffer = ByteBuffer.allocate(512);
          int count = client.read(byteBuffer);
          if (count > 0) {
            byteBuffer.flip();
            String receivedMsg = String.valueOf(CHARSET_UTF8.decode(byteBuffer));
            System.out.println(receivedMsg);
          }

        }
      }
      // IM: 当处理完对应key事件后，必须要移除。
      iterator.remove();
    }
  }
}