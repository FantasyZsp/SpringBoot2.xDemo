package xyz.mydev.jvm.bytecode.dynamicproxy;

/**
 * @author ZSP
 */
public class RealSubject implements Subject, Marker {
  @Override
  public void request() {
    System.out.println("RealSubject request");
  }

  @Override
  public void valid() {
    System.out.println("RealSubject valid");
  }

  @Override
  public void mark() {
    System.out.println("mark");
  }
}
