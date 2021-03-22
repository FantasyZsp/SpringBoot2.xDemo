package xyz.mydev.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个选择器拥有多个通道
 *
 * @author ZSP
 */
public class ServerSocketChannelTest {
  public static void main(String[] args) throws IOException {

    Selector selector = Selector.open();
    int[] ports = {5001, 5002, 5003, 5004, 5005};

    for (int port : ports) {
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
      serverSocketChannel.bind(inetSocketAddress);
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("监听端口：" + port);
    }

    while (true) {
      int select = selector.select();
      System.out.println(select);
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      System.out.println("selectionKeys: " + selectionKeys);

      Iterator<SelectionKey> iterator = selectionKeys.iterator();

      while (iterator.hasNext()) {
        SelectionKey selectionKey = iterator.next();
        if (selectionKey.isAcceptable()) {
          ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
          SocketChannel socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);
          socketChannel.register(selector, SelectionKey.OP_READ);

          System.out.println("获得客户端连接: " + socketChannel);
        } else if (selectionKey.isReadable()) {
          SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
          int byteReads = NioUtil.receiveAndReturnMsg(socketChannel);
        }
        iterator.remove();
      }

      selectionKeys.clear();
    }
  }


}
