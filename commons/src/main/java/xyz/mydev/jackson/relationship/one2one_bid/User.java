package xyz.mydev.jackson.relationship.one2one_bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhao   2018/09/10 18:18
 * @description 用户表。一个用户拥有一个计划，也可以没有计划。
 */
@Getter
@Setter
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  @Column(columnDefinition = "timestamp(3) NULL")
  private LocalDateTime birthday;

  @Embedded
  private Address address;

  @OneToOne(mappedBy = "user")
  @JsonIgnore
  private Plan plan;


  public User() {
  }

  @Override
  public String toString() {
    return "OneUser{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", birthday=" + birthday +
      ", address=" + address +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (!id.equals(user.id)) return false;
    return username.equals(user.username);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + username.hashCode();
    return result;
  }
}
