package xyz.mydev.jdk;

/**
 * @author ZSP
 */
public class RealSubject implements Subject {
  @Override
  public Object request(Object requestParam) {
    System.out.println("RealSubject execute request()");
    return "RealSubject";
  }
}
