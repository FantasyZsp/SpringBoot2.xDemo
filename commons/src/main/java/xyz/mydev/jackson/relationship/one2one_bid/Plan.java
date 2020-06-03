package xyz.mydev.jackson.relationship.one2one_bid;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhao
 * @date 2018/09/08 18:42
 * @description 计划表：一个用户拥有一个计划，也可以没有计划。但一个计划必须有一个用户。
 */

@Entity
@Getter
@Setter
@Table(name = "plan")
public class Plan implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "char(36)", nullable = false)
  private String code;
// 0709eb81-ce65-4b95-8ccc-19b102d3f819

  @Column(columnDefinition = "timestamp(3) NULL")
  private Instant instant;

  private Instant instant2;

  private Date date;

  private Long longTime;

  private String notes;

  @Column(columnDefinition = "char(10)")
  private String mark;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT), nullable = false)
  private User user;

  public Plan() {
  }


  @Override
  public String toString() {
    return "ManyPlan{" +
      "id=" + id +
      ", code='" + code + '\'' +
      ", instant=" + instant +
      ", instant2=" + instant2 +
      ", date=" + date +
      ", longTime=" + longTime +
      ", notes='" + notes + '\'' +
      ", mark='" + mark + '\'' +
      ", user=" + user +
      '}';
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Plan plan = (Plan) o;

    if (!id.equals(plan.id)) return false;
    return code.equals(plan.code);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(code);
    return result;
  }
}
