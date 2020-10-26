package xyz.mydev.baidu.ai.face.constant;

/**
 * 百度智能云文档
 * {@link <a href="https://ai.baidu.com/ai-doc/FACE/Gk37c1uzc"/>}
 * <p>
 * 默认NONE
 *
 * @author ZSP
 */
public enum LivenessControlEnum {
  /**
   * 活体检测控制
   */
  NONE("NONE", "不进行控制"),
  LOW("LOW", "较低的活体要求(高通过率 低攻击拒绝率)"),
  NORMAL("NORMAL", "一般的活体要求(平衡的攻击拒绝率, 通过率)"),
  HIGH("HIGH", "较高的活体要求(高攻击拒绝率 低通过率)");

  private final String code;
  private final String desc;

  LivenessControlEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
