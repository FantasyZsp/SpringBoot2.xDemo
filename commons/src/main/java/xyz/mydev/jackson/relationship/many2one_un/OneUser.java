package xyz.mydev.jackson.relationship.many2one_un;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhao  2018/09/10 18:18
 * @description 用户表。一个用户拥有一个计划，也可以没有计划。
 */
@Entity
@Getter
@Setter
@Table(name = "one_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class OneUser implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  @OneToMany(mappedBy = "oneUser")
  @JsonIgnore
  private List<ManyPlan> manyPlans;

  @Override
  public String toString() {
    return "OneUser{" +
      "id=" + id +
      ", username='" + username + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OneUser OneUser = (OneUser) o;

    if (!id.equals(OneUser.id)) return false;
    return username.equals(OneUser.username);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + username.hashCode();
    return result;
  }
}
