package xyz.mydev.util;

/**
 * @author ZSP
 */
public class RandomUtils {

  public static void main(String[] args) {

    System.out.println(xorShift(new Object().hashCode() ^ (int) System.nanoTime()));
  }

  /**
   * @param y 基于目标值的hashcode和时间戳
   */
  public static int xorShift(int y) {

    y ^= (y << 6);
    y ^= (y >>> 21);
    y ^= (y << 7);

    return y;

  }
}
