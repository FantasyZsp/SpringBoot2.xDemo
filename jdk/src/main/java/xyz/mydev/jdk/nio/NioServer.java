package xyz.mydev.jdk.nio;

import org.springframework.util.Assert;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Server
 *
 * @author ZSP
 */
public class NioServer {
  private static Map<SocketChannel, String> CLIENT_MAP = new HashMap<>();

  public static void main(String[] args) throws Exception {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(5000));

    Selector selector = Selector.open();
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    while (true) {
      int eventCount = selector.select();
      Assert.isTrue(eventCount != 0, "test");
      Set<SelectionKey> selectionKeys = selector.selectedKeys();

      Iterator<SelectionKey> iterator = selectionKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey selectionKey = iterator.next();
        // 处理连接
        if (selectionKey.isAcceptable()) {
          // 对于 OP_ACCEPT事件，注册的都是 ServerSocketChannel
          ServerSocketChannel severChannel = (ServerSocketChannel) selectionKey.channel();
          SocketChannel client = severChannel.accept();
          client.configureBlocking(false);
          client.register(selector, SelectionKey.OP_READ);

          String key = UUID.randomUUID().toString();
          CLIENT_MAP.put(client, key);

          System.out.println("客户端:[" + key + ":" + client.socket().getPort() + "]已连接");
          // 处理客户端发送的数据
        } else if (selectionKey.isReadable()) {
          SocketChannel client = (SocketChannel) selectionKey.channel();
          if (client.isConnected()) {
            NioUtil.receiveAndBroadcast(client, CLIENT_MAP.get(client), new ArrayList<>(CLIENT_MAP.keySet()));
          }
        }
        iterator.remove();
      }
    }
  }
}
