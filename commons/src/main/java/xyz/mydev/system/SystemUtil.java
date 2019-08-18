package xyz.mydev.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

public class SystemUtil {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException, SocketException {
    print(System.getenv());
    print(System.getProperties());
    print(System.getSecurityManager());

    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
    while (networkInterfaces.hasMoreElements()) {
      System.out.println("===============start=============");
      NetworkInterface networkInterface = networkInterfaces.nextElement();
      print(networkInterface.getName());
      print(networkInterface.getDisplayName());
      print(networkInterface.getHardwareAddress());
      print(networkInterface.getIndex());
//            print(networkInterfaces.nextElement().getInetAddresses());
      print(networkInterface.getInterfaceAddresses());
      print(networkInterface.getParent());
//            print(networkInterfaces.nextElement().getSubInterfaces());
      Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
      while (inetAddresses.hasMoreElements()) {
        InetAddress inetAddress = inetAddresses.nextElement();
        System.out.println("getAddress: " + Arrays.toString(inetAddress.getAddress()));
        System.out.println("getCanonicalHostName: " + inetAddress.getCanonicalHostName());
        System.out.println("getHostAddress: " + inetAddress.getHostAddress());
        System.out.println("getHostName: " + inetAddress.getHostName());
      }


      System.out.println("===============end=============");
    }

  }

  private static void print(Object obj) throws JsonProcessingException {
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
  }
}
