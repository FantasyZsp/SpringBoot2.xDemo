package xyz.mydev.baidu.ai.face.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;
import xyz.mydev.baidu.ai.face.constant.LivenessControlEnum;
import xyz.mydev.baidu.ai.face.constant.QualityControlEnum;

import java.util.Objects;

/**
 * @author zhaosp
 * @link <a href="">https://ai.baidu.com/ai-doc/FACE/Gk37c1uzc</a>
 */
@Getter
@Setter
@ToString
public class ControlProperties {
  private String qualityControl = QualityControlEnum.NONE.getCode();
  private String livenessControl = LivenessControlEnum.NONE.getCode();
  private Integer matchThreshold = 80;
  private Integer maxFaceNum = 10;
  private Integer maxUserNum = 20;

  public ControlProperties() {
  }

  private ControlProperties(String qualityControl,
                            String livenessControl,
                            Integer matchThreshold,
                            Integer maxFaceNum,
                            Integer maxUserNum) {
    this.qualityControl = qualityControl;
    this.livenessControl = livenessControl;
    this.matchThreshold = matchThreshold;
    this.maxFaceNum = maxFaceNum;
    this.maxUserNum = maxUserNum;
  }

  public static ControlProperties of(QualityControlEnum qualityControl,
                                     LivenessControlEnum livenessControl,
                                     Integer matchThreshold) {

    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);
    Assert.isTrue(matchThreshold >= 0 && matchThreshold <= 100, "invalid matchThreshold");
    return ControlProperties.of(qualityControl, livenessControl, matchThreshold, 10, 20);
  }

  public static ControlProperties of(QualityControlEnum qualityControl,
                                     LivenessControlEnum livenessControl,
                                     Integer matchThreshold,
                                     Integer maxFaceNum,
                                     Integer maxUserNum) {

    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);
    Assert.isTrue(matchThreshold >= 0 && matchThreshold <= 100, "invalid matchThreshold");
    return new ControlProperties(qualityControl.getCode(), livenessControl.getCode(), matchThreshold, maxFaceNum, maxUserNum);
  }

  public static ControlProperties buildNormal() {
    return ControlProperties.of(QualityControlEnum.NORMAL, LivenessControlEnum.NORMAL, 80);
  }

  public static ControlProperties buildLow(Integer matchThreshold) {
    return ControlProperties.of(QualityControlEnum.LOW, LivenessControlEnum.LOW, matchThreshold);
  }

  public static ControlProperties buildHigh(Integer matchThreshold) {
    return ControlProperties.of(QualityControlEnum.HIGH, LivenessControlEnum.HIGH, matchThreshold);
  }

}