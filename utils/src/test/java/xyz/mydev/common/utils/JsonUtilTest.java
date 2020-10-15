package xyz.mydev.common.utils;

import org.junit.Test;
import xyz.mydev.common.beans.vo.NumberVO;

/**
 * @author ZSP
 */
public class JsonUtilTest {
  @Test
  public void test() {
    System.out.println(JsonUtil.obj2String(NumberVO.of(1)));
  }

}