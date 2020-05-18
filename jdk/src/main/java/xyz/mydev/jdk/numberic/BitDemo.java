package xyz.mydev.jdk.numberic;

/**
 * @author ZSP
 */
public class BitDemo {
  public static void main(String[] args) {
    System.out.println(1 << 1);
    System.out.println(1 << 1 | 1 << 2);

    long i = 1 << 1;

    System.out.println(Runtime.getRuntime().availableProcessors());
  }
}
