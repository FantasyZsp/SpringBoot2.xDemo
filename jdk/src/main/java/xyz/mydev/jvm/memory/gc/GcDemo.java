package xyz.mydev.jvm.memory.gc;

/**
 * @author ZSP
 */
public class GcDemo {

  private static final int _1MB = 1024 * 1024;

  public static void testTenuringThreshold() {
    byte[] allocation1, allocation2, allocation3;
    allocation1 = new byte[_1MB / 4];
    allocation2 = new byte[4 * _1MB];
    allocation3 = new byte[4 * _1MB];
    allocation3 = null;
    allocation3 = new byte[4 * _1MB];
  }

  public static void main(String[] args) {
    Integer nums[] = new Integer[1];

    System.out.println(nums[0]);

   }
}
