package xyz.mydev.jdk.stream.parallel;

/**
 * @author ZSP
 */
public class SimpleDemo {
  public static void main(String[] args) {
    int a = 1000;
    int b = 1000;
    Integer nubmerA = 1000;
    Integer nubmerB = 1000;

    System.out.println(1000 == 1000);
    System.out.println(a == b);
    System.out.println(a == 1000);
    System.out.println(nubmerA == 1000);
    System.out.println(nubmerA == nubmerB);
    System.out.println(nubmerA.equals(nubmerB));
  }
}
