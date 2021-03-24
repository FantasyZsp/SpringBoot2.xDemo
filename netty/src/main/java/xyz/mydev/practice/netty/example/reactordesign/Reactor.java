package xyz.mydev.practice.netty.example.reactordesign;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Reactor模式的单多关注点：
 * 1. 处理连接的方式：少数线程处理多个连接
 * 2. 连接处理与IO读写分离
 * 3. IO读写与业务处理分离
 * 4. 以上，各个分离点做异步处理
 *
 * @author ZSP
 */
public class Reactor implements Runnable {

  /**
   * 当Selector变更为数组时，对应了多Reactor模式。多Reactor模式下，分主次，可以对应netty的bossGroup和workerGroup。
   */
  final Selector selector;
  final ServerSocketChannel serverSocketChannel;

  public Reactor(int port) throws IOException {
    selector = Selector.open();
    serverSocketChannel = ServerSocketChannel.open();
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress(port));
    serverSocketChannel.configureBlocking(false);

    SelectionKey registerKey = serverSocketChannel.register(selector, SelectionKey.OP_READ);
    registerKey.attach(new Accepter(serverSocketChannel, selector));


  }

  @Override
  public void run() {

    try {
      while (!Thread.interrupted()) {
        selector.select();
        Set<SelectionKey> selected = selector.selectedKeys();
        for (SelectionKey selectionKey : selected) {
          // 分发
          dispatch(selectionKey);
        }
        selected.clear();
      }
    } catch (IOException ignored) {
    }
  }

  void dispatch(SelectionKey selectionKey) {
    Runnable runnable = (Runnable) selectionKey.attachment();
    if (runnable != null) {
      runnable.run();
    }
  }


}

class Accepter implements Runnable {

  ServerSocketChannel serverSocketChannel;
  Selector selector;

  public Accepter(ServerSocketChannel serverSocketChannel, Selector selector) {
    this.serverSocketChannel = serverSocketChannel;
    this.selector = selector;
  }

  @Override
  public void run() {

    try {
      SocketChannel socketChannel = serverSocketChannel.accept();

      if (socketChannel != null) {
        // 这里如果不新建线程，那么所有的处理都会交给IO线程去做。可以引入线程池
        new Thread(new Handler(serverSocketChannel, selector)).start();
      }

    } catch (IOException exception) {
      exception.printStackTrace();
    }

  }
}

/**
 * 具体业务的处理。这里包含了IO数据的读取，只是说不用等待IO准备的过程，可以直接读取写入了
 */
class Handler implements Runnable {
  ServerSocketChannel serverSocketChannel;
  Selector selector;
  SelectionKey sk;

  public Handler(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
    this.serverSocketChannel = serverSocketChannel;
    serverSocketChannel.configureBlocking(false);
    this.selector = selector;

    sk = serverSocketChannel.register(selector, 0);
    sk.attach(this);
    sk.interestOps(SelectionKey.OP_READ);
    selector.wakeup();
  }

  @Override
  public void run() {

    try {
      if (sk.isReadable()) {
        read();
      } else if (sk.isWritable()) {
        write();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private void write() {
    // process write and change interestOps to read
    process();
  }

  private void process() {

  }

  private void read() {
    // process read and change interestOps to write or cancelled

    process();
  }
}
