package xyz.mydev.jdk.baseapi.numberic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ZSP
 */
public class BigDecimalDemo {

  public static void main(String[] args) {

    BigDecimal bigDecimal = new BigDecimal(11L);
    BigDecimal bigDecimal2 = new BigDecimal(3L);
    BigDecimal divide = bigDecimal.divide(bigDecimal2, 2, RoundingMode.HALF_UP);

    System.out.println(divide.doubleValue());
    System.out.println(Math.round(divide.doubleValue()));

    System.out.println(divide);
    System.out.println(divide.scale());


  }

}
