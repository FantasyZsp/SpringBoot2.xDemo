package xyz.mydev.jdk.map.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 需要累加到task的奖励
 *
 * @author ZSP
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class RewardToAccumulateValue {
  /**
   * 增加的完成量
   */
  private Integer completeCount;
  /**
   * 增加的金钱
   */
  private Integer money;
  /**
   * 增加的经验
   */
  private Integer experience;
  /**
   * 增加的积分
   */
  private Integer integral;
  /**
   * 增加的声望
   */
  private Integer prestige;

  public static RewardToAccumulateValue init() {
    return RewardToAccumulateValue.builder()
      .completeCount(0)
      .money(0)
      .experience(0)
      .integral(0)
      .prestige(0)
      .build();
  }


  public void accumulate(RewardToAccumulateValue value) {
    if (value == null) {
      return;
    }

    this.completeCount = this.completeCount + value.getCompleteCount();
    this.money = this.money + value.getMoney();
    this.experience = this.experience + value.getExperience();
    this.integral = this.integral + value.getIntegral();
    this.prestige = this.prestige + value.getPrestige();
  }

  public void accumulateSync(RewardToAccumulateValue value) {
    synchronized (this) {
      accumulate(value);
    }
  }
}
