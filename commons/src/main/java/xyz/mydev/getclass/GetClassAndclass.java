package xyz.mydev.getclass;

/**
 * @author  zhao
 * @date  2018/08/10 18:25
 * @description
 */
public class GetClassAndclass {
  public static void main(String[] args) {
    String str = "";
    String str2 = "11";
    Object obj = (Object) str2;
    System.out.println(obj.getClass());
    System.out.println(Object.class);
    System.out.println(str.getClass());
    System.out.println(str.getClass().getName());
    System.out.println(String.class);
    System.out.println((obj instanceof String));
  }
}
