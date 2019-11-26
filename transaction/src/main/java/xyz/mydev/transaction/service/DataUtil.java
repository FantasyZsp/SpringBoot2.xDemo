package xyz.mydev.transaction.service;

import org.apache.commons.lang3.RandomUtils;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author ZSP
 */
public class DataUtil {
  public static Girl generate() {
    Girl girl = new Girl();
    girl.setAge(RandomUtils.nextInt(0, 200));
    girl.setCupSize(String.valueOf(RandomUtils.nextBytes(1)[0]));
    return girl;
  }

}
