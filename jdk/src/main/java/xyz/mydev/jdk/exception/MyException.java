package xyz.mydev.jdk.exception;

/**
 * @author ZSP
 */
public class MyException extends Exception {
  public MyException() {

    super("default error msg"); // 也可以这么传
  }

  public MyException(String message) {
    super();// 也可以不传，但是message就无法传递到父类中去使用了
  }
}
