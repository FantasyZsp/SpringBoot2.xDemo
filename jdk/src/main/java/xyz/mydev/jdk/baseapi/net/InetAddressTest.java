package xyz.mydev.jdk.baseapi.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhaosp
 */
public class InetAddressTest {

  @Test
  public void newInetAddress() throws UnknownHostException {

    InetAddress byName = InetAddress.getByName("www.baidu.com");
    System.out.println(byName);
    System.out.println(byName);

  }
}
