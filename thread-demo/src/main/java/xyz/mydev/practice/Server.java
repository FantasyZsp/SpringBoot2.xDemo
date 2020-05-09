package xyz.mydev.practice;

/**
 * @author ZSP
 */
public class Server {

  private volatile KeyValueCacheDemo demo;


  public static void main(String[] args) {

  }

  public String testConcurrent(String key) {

    if (demo != null) {
      String value = demo.getValue(key);
      if (value != null) {
        return value;
      }
    }


    return null;

  }
}
