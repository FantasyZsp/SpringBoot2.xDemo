package xyz.mydev.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZSP
 * @date 2018/10/20 19:37
 * @description 判断两组时间段跨度
 * Status        变量                         枚举            区域图示
 * -2  左全跨     isAllBeforeMinDate          BEFORE_MIN      ()       -> MIN
 * -1  左跨       isCrossMinDate              CROSS_MIN       ([MIN])  -> MAX
 * 0   命中       isBetweenMinAndMax          BETWEEN         MIN[]MAX
 * 1   右跨       isCrossMaxDate              CROSS_MAX       MIN      -> ([MAX])
 * 2   右全跨     isAllAfterMaxDate           AFTER_MAX       MAX      -> ()
 * 3   全跨       isCrossAllAndNotAtEdge      CROSS          (MIN,MAX)
 */
public enum TimesCrossStatus {

  /**
   * 左全跨:  ()    -> MIN
   */
  BEFORE_MIN(-2, "左全跨:  ()    -> MIN"),
  /**
   * 左跨:  ([MIN]) -> MAX
   */
  CROSS_MIN(-1, "左跨:  ([MIN]) -> MAX"),

  /**
   * 命中:  MIN[]MAX
   */
  BETWEEN(0, "命中:  MIN[]MAX"),

  /**
   * 右跨:  MIN   -> ([MAX])
   */
  CROSS_MAX(1, "右跨:  MIN   -> ([MAX])"),

  /**
   * 右全跨: MAX  -> ()
   */
  AFTER_MAX(2, "右全跨: MAX  -> ()"),

  /**
   * 全跨:  (MIN,MAX)
   */
  CROSS(3, "全跨:  (MIN,MAX)");


  public static final Map<Integer, TimesCrossStatus> CROSS_STATUS_MAP = new HashMap<>();

  static {
    for (TimesCrossStatus item : TimesCrossStatus.values()) {
      CROSS_STATUS_MAP.put(item.code, item);
    }
  }

  private int code;
  private String msg;

  TimesCrossStatus(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
