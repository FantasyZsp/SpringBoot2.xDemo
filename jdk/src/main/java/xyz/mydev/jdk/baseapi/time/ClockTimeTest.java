package xyz.mydev.jdk.baseapi.time;

/**
 * @author ZSP
 */
public class ClockTimeTest {

  /**
   * 时钟和单调钟区别
   */
  public static void main(String[] args) {


    // 1622012328708
    // 167684887957700

    // 1622012343941
    // 167700121136400
    System.out.println(System.currentTimeMillis());
    System.out.println(System.nanoTime());

  }

}
