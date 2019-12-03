package xyz.mydev.jdk.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ZSP
 */
public class NioUtil {
  private static final Charset CHARSET;

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
        receivedMsg.append(String.valueOf(CHARSET.decode(byteBuffer).array()));
      }
    }

    if (socketChannel.isConnected()) {
      String result = "\n收到客户端[" + clientKey + "]消息: " + receivedMsg.toString();
      System.out.println(result);
      System.out.println("读取" + byteReads + "个字节");
      socketChannel.write(CHARSET.encode(result));
    }
    return byteReads;
  }

  public static String receiveMsg(SocketChannel socketChannel, String clientKey) {
    int byteReads = 0;
    StringBuilder receivedMsg = new StringBuilder();


    while (true) {
      ByteBuffer byteBuffer = ByteBuffer.allocate(512);
      byteBuffer.clear();
      int read = 0;
      try {
        read = socketChannel.read(byteBuffer);
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
      byteReads += read;
      if (read == -1) {
        System.out.println("客户端[" + clientKey + "]已断开连接");
        try {
          socketChannel.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      }
      if (read == 0) {
        break;
      }
      if (read > 0) {
        byteBuffer.flip();
        receivedMsg.append(String.valueOf(CHARSET.decode(byteBuffer).array()));
      }
    }
    String result = "\n收到客户端[" + clientKey + "]消息: " + receivedMsg.toString();
    if (socketChannel.isConnected()) {
      System.out.println(result);
      System.out.println("读取" + byteReads + "个字节");
    } else {
      result = "客户端[" + clientKey + "]已断开连接";
    }
    return result;
  }

  public static void broadcast(List<SocketChannel> socketChannelList, String msg) {
    for (SocketChannel socketChannel : socketChannelList) {
      if (socketChannel.isConnected()) {
        try {
          socketChannel.write(CHARSET.encode(msg));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void receiveAndBroadcast(SocketChannel socketChannel, String clientKey, List<SocketChannel> socketChannelList) {
    String receiveMsg = receiveMsg(socketChannel, clientKey);
    broadcast(socketChannelList, receiveMsg);
  }
}
