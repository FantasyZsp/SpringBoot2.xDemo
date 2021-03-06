package xyz.mydev.jdk.classfeature.resource;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ZSP
 */
public class Demo {
  private static final String modulePath = System.getProperty("user.dir");


  @Test
  public void getResource1() throws IOException {
    File file = new File(modulePath + "/jdk/src/main/resources/file");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String s = "";
    while ((s = br.readLine()) != null) {
      System.out.println(s);
    }
  }

  @Test
  public void getResource2() throws IOException {
    File file = new File("C:/Users/User/Documents/sishu-canal-client-adapter/launcher/target/client-adapter.launcher-1.1.4.jar!/BOOT-INF/classes!/");
    System.out.println(file.exists());
  }

  @Test
  public void getResource3() throws IOException {
    InputStream resourceAsStream = this.getClass().getResourceAsStream("C:/Users/User/Documents/sishu-canal-client-adapter/launcher/target/client-adapter.launcher-1.1.4.jar!/BOOT-INF/classes!/");
    BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
    String s = "";
    while ((s = br.readLine()) != null) {
      System.out.println(s);
    }
  }
}
