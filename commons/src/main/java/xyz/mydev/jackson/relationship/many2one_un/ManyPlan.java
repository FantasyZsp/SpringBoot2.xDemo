package xyz.mydev.jackson.relationship.many2one_un;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * @author zhao
 * @date 2018/09/08 18:42
 * @description 用户一，计划多
 */

@Entity
@Table(name = "many_plan")
@Getter
@Setter
public class ManyPlan implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "char(36)", nullable = false)
  private String code;

  @Column(columnDefinition = "timestamp(3) NULL")
  private Instant instant;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT), nullable = false)
  private OneUser oneUser;

  public ManyPlan() {
  }

  @Override
  public String toString() {
    return "ManyPlan{" +
      "id=" + id +
      ", code='" + code + '\'' +
      ", instant=" + instant +
      ", oneUser=" + oneUser +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ManyPlan ManyPlan = (ManyPlan) o;

    if (!id.equals(ManyPlan.id)) return false;
    return code.equals(ManyPlan.code);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(code);
    return result;
  }
}
