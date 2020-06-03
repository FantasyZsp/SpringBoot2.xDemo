package xyz.mydev.base;

/**
 * @author ZSP
 */
public class ThreadApi {
  public static void main(String[] args) {
    Thread thread = new Thread();
    System.out.println(thread.isAlive());
    thread.start();
    System.out.println(thread.isAlive());
  }
}
