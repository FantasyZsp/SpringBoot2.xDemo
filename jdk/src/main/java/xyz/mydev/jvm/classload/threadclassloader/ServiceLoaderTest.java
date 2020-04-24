package xyz.mydev.jvm.classload.threadclassloader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ServiceLoader;

/**
 * @author ZSP
 */
public class ServiceLoaderTest {
  public static void main(String[] args) throws Exception {
    testJdbc();
  }


  public static void test() {
    ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
    for (Driver next : serviceLoader) {
      System.out.println("driver: " + next.getClass() + ", load by " + next.getClass().getClassLoader());
    }
    System.out.println("serviceLoader load by : " + serviceLoader.getClass().getClassLoader());
  }


  public static void testJdbc() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/avengers?useSSL=false&serverTimezone=Asia/Shanghai", "root", "123456");
  }


}
