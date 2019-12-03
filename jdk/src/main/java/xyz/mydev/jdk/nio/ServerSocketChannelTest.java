package xyz.mydev.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
    }

    while (true) {
      int select = selector.select();
      Set<SelectionKey> selectionKeys = selector.selectedKeys();


      for (SelectionKey selectionKey : selectionKeys) {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        channel.configureBlocking(false);
        SocketChannel socketChannel = channel.accept();
        if (selectionKey.isAcceptable()) {
          System.out.println("========isAcceptable========");
          System.out.println(socketChannel);
        } else if (selectionKey.isConnectable()) {
          System.out.println("========isConnectable=========");

        } else if (selectionKey.isReadable()) {
          System.out.println("========isReadable=========");
        } else if (selectionKey.isValid()) {
          System.out.println("========isValid=========");
        } else if (selectionKey.isWritable()) {
          System.out.println("========isWritable=========");
        }
      }
    }
  }
}
