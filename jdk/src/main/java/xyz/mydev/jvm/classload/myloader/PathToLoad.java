package xyz.mydev.jvm.classload.myloader;

/**
 * @author ZSP
 */
public class PathToLoad {
  public static void main(String[] args) {
//    System.out.println(System.getProperty("sun.boot.class.path"));
//    System.out.println(System.getProperty("java.ext.dirs"));
//    System.out.println(System.getProperty("java.class.path"));

    System.out.println(ClassLoader.getSystemClassLoader().getClass().getClassLoader());
  }
}
