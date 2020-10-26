package xyz.mydev.baidu.ai.face.property;

import lombok.Getter;
import lombok.Setter;
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
public class ControlProperties {
  private String qualityControl = QualityControlEnum.NONE.getCode();
  private String livenessControl = LivenessControlEnum.NONE.getCode();
  private double score = 80;

  public ControlProperties() {
  }

  private ControlProperties(String qualityControl,
                            String livenessControl,
                            double score) {
    this.qualityControl = qualityControl;
    this.livenessControl = livenessControl;
    this.score = score;
  }

  public static ControlProperties of(QualityControlEnum qualityControl,
                                     LivenessControlEnum livenessControl,
                                     double score) {

    Objects.requireNonNull(qualityControl);
    Objects.requireNonNull(livenessControl);
    Assert.isTrue(score >= 0 && score <= 100, "invalid score");
    return new ControlProperties(qualityControl.getCode(), livenessControl.getCode(), score);
  }

  public static ControlProperties buildNormal() {
    return ControlProperties.of(QualityControlEnum.NORMAL, LivenessControlEnum.NORMAL, 80);
  }

  public static ControlProperties buildLow(double score) {
    return ControlProperties.of(QualityControlEnum.LOW, LivenessControlEnum.LOW, score);
  }

  public static ControlProperties buildHigh(double score) {
    return ControlProperties.of(QualityControlEnum.HIGH, LivenessControlEnum.HIGH, score);
  }

}