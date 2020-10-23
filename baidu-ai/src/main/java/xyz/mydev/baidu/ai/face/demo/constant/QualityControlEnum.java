package xyz.mydev.baidu.ai.face.demo.constant;

/**
 * 百度智能云文档{@link <a href="https://ai.baidu.com/ai-doc/FACE/Gk37c1uzc"/>}
 * <p>
 * 默认NONE
 *
 * @author ZSP
 */
public enum QualityControlEnum {
  /**
   * 图片质量控制
   */
  NONE("NONE", "不进行控制"),
  LOW("LOW", "较低的质量要求"),
  NORMAL("NORMAL", "一般的质量要求"),
  HIGH("HIGH", "较高的质量要求");

  private final String code;
  private final String desc;

  QualityControlEnum(String code, String desc) {
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
