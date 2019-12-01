package xyz.mydev.jdk.nio;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ZSP
 */
public class BufferTest {
  private final String modulePath = System.getProperty("user.dir");

  public static void main(String[] args) throws Exception {
    System.out.println(System.getProperty("user.dir"));
    System.out.println(System.getProperty("user.home"));
  }

  @Before
  public void before() {
    System.out.println(modulePath);
    System.out.println(System.getProperty("user.home"));
  }


  @Test
  public void testChannelRead() throws Exception {
    FileInputStream fileInputStream = new FileInputStream(modulePath + "/src/main/resources/nio-demo.txt");
    FileChannel channel = fileInputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
    channel.read(byteBuffer);
    byteBuffer.flip();

    while (byteBuffer.hasRemaining()) {
      System.out.println((char) byteBuffer.get());
    }
  }

  @Test
  public void testChannelWrite() throws Exception {
    String filePath = modulePath + "/src/main/resources/nio-write-test.txt";

    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
    FileChannel channel = fileOutputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    byte[] content = "test write".getBytes();
    for (byte b : content) {
      byteBuffer.put(b);
    }

    System.out.println(byteBuffer.limit());
    System.out.println(byteBuffer.position());
    System.out.println(byteBuffer.capacity());
    byteBuffer.flip();
    System.out.println(byteBuffer.limit());
    System.out.println(byteBuffer.position());
    System.out.println(byteBuffer.capacity());
    channel.write(byteBuffer);

    System.out.println(FileUtils.readFileToString(new File(filePath)));
  }
}
