package xyz.mydev.jvm.memory;

/**
 * 栈溢出测试
 * 设定虚拟机参数-Xss160k
 *
 * @author ZSP
 */
public class StackOverflowErrorTest {
  private int length = 0;

  public void test() {
    length++;
//    ThreadUtils.join(300);
    test();
  }

  public static void main(String[] args) {
    StackOverflowErrorTest test = new StackOverflowErrorTest();
    try {
      test.test();
    } catch (Throwable tx) {
      System.out.println(test.length);
      tx.printStackTrace();
    }
  }
}
