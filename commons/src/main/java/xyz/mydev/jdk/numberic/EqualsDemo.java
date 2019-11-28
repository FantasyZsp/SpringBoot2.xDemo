package xyz.mydev.jdk.numberic;

/**
 * @author ZSP
 */
public class EqualsDemo {

  public static void main(String[] args) {
    Integer num1 = 1;
    Integer num2 = 2;
    Integer num3 = null;
    Integer num4 = 2;

    System.out.println(num1.equals(num2));
    System.out.println(num1.equals(num3));
    System.out.println(num2.equals(num4));

    System.out.println(num3 instanceof Integer);
  }
}
