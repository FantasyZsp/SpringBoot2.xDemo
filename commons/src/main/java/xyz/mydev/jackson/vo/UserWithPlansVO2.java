package xyz.mydev.jackson.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao  2018/09/12 10:40
 * @description 测试使用mapper
 */
@Getter
@Setter
public class UserWithPlansVO2 implements Serializable {
  private Long userId;
  private String userName;
  private List<Plan> plans = new ArrayList<>();

  @Getter
  @Setter
  public static class Plan {
    private Long planId;
    private String code;
    private Instant instant;

    public Plan() {
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Plan plan = (Plan) o;

      if (!planId.equals(plan.planId)) return false;
      return code.equals(plan.code);
    }

    @Override
    public int hashCode() {
      int result = planId.hashCode();
      result = 31 * result + code.hashCode();
      return result;
    }
  }


  public UserWithPlansVO2() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserWithPlansVO2 that = (UserWithPlansVO2) o;

    if (!userId.equals(that.userId)) return false;
    return userName.equals(that.userName);
  }

  @Override
  public int hashCode() {
    int result = userId.hashCode();
    result = 31 * result + userName.hashCode();
    return result;
  }
}


