package xyz.mydev.jackson.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @date 2018/09/12 10:40
 * @description 测试mapper用法。此处保持属性名一致。
 */
@Getter
@Setter
public class UserWithPlansVO implements Serializable {
  @JsonProperty("userId")
  private Long id;

  @JsonProperty("userName")
  private String username;

  @JsonProperty("plans")
  private List<MPlanVO> mPlans = new ArrayList<>();

  public UserWithPlansVO() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserWithPlansVO that = (UserWithPlansVO) o;

    if (!id.equals(that.id)) return false;
    return username.equals(that.username);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + username.hashCode();
    return result;
  }
}


