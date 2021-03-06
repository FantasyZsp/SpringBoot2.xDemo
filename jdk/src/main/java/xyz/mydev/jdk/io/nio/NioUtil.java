package xyz.mydev.jdk.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
public class NioUtil {
  private static final Charset CHARSET;
  private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
  private static final String CLIENT_EXIT = "-1";


  static {
    String os = System.getProperty("os.name");
    CHARSET = os.toLowerCase().startsWith("win") ? Charset.forName("gbk") : StandardCharsets.UTF_8;
  }


  public static int receiveAndReturnMsg(SocketChannel socketChannel) throws IOException {
    return receiveAndReturnMsg(socketChannel, String.valueOf(socketChannel.socket().getLocalPort()));
  }

  public static int receiveAndReturnMsg(SocketChannel socketChannel, String clientKey) throws IOException {
    int byteReads = 0;
    StringBuilder receivedMsg = new StringBuilder();

    while (true) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(512);
      byteBuffer.clear();
      int read = socketChannel.read(byteBuffer);
      byteReads += read;
      if (read == -1) {
        System.out.println("客户端[" + clientKey + "]已断开连接");
        socketChannel.close();
        break;
      }
      if (read == 0) {
        break;
      }
      if (read > 0) {
        byteBuffer.flip();
        receivedMsg.append(String.valueOf(CHARSET_UTF8.decode(byteBuffer).array()));
      }
    }

    if (socketChannel.isConnected()) {
      String result = "\n收到客户端[" + clientKey + "]消息: " + receivedMsg.toString();
      System.out.println(result);
      System.out.println("读取" + byteReads + "个字节");
      socketChannel.write(CHARSET_UTF8.encode(result));
    }
    return byteReads;
  }

  public static String receiveMsg(SocketChannel socketChannel, String clientKey) {
    int byteReads = 0;
    StringBuilder receivedMsg = new StringBuilder();
    boolean close = false;
    while (true) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(512);
      byteBuffer.clear();
      int read;
      try {
        read = socketChannel.read(byteBuffer);
      } catch (IOException e) {
        close = true;
        read = -1;
        byteReads += read;
        e.printStackTrace();
        break;
      }
      byteReads += read;
      if (read == -1) {
        close = true;
        break;
      }
      if (read == 0) {
        break;
      }
      if (read > 0) {
        byteBuffer.flip();
        receivedMsg.append(String.valueOf(CHARSET_UTF8.decode(byteBuffer).array()));
      }
    }
    String result = "\n收到客户端[" + clientKey + "]消息: " + receivedMsg.toString();
    if (!close) {
      System.out.println(result);
      System.out.println("读取" + byteReads + "个字节");
    } else {
      try {
        socketChannel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      result = CLIENT_EXIT;
    }
    return result;
  }

  public static void broadcast(List<SocketChannel> socketChannelList, String msg) {
    for (SocketChannel socketChannel : socketChannelList) {
      if (socketChannel.isConnected()) {
        try {
          socketChannel.write(CHARSET_UTF8.encode(msg));
        } catch (IOException e) {
          e.printStackTrace();
          try {
            socketChannel.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }

  public static void receiveAndBroadcast(SocketChannel socketChannel, String clientKey, List<SocketChannel> socketChannelList) {
    String receiveMsg = receiveMsg(socketChannel, clientKey);
    if (CLIENT_EXIT.equals(receiveMsg)) {
      System.out.println("客户端[" + clientKey + "]已断开连接");
    }
    socketChannelList = new ArrayList<>(socketChannelList);
    socketChannelList.remove(socketChannel);
    broadcast(socketChannelList, receiveMsg);
  }
}
