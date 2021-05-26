package xyz.mydev.jdk.baseapi.util.datatype.numberic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * @author ZSP
 * @date 2018/10/29 10:06
 * @description
 */
public class numberCaculator {
  private static final NumberFormat PERCENT = NumberFormat.getPercentInstance();

  static {
    // 百分比精确度 0.01%
    PERCENT.setMinimumFractionDigits(2);
  }

  public static void main(String[] args) {

    BigInteger num1 = new BigInteger("3552");
    BigInteger num2 = new BigInteger("5406");
    BigDecimal num5 = new BigDecimal(num2).setScale(2);
    System.out.println(num5);

    System.out.println(num1.divide(num2));
    System.out.println(num1.doubleValue() / num2.doubleValue());
    System.out.println(PERCENT.format(num1.doubleValue() / num2.doubleValue()));
    System.out.println(PERCENT.format(num1.doubleValue() / 0));
  }
}
