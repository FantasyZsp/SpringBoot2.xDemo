package xyz.mydev.datastructure.set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhao   2018/08/13 12:16
 * @description
 */
@Getter
@Setter
public class User {
  private Integer id;
  private String name;

  public User() {
  }

  public User(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  @Override
  public String toString() {
    return "Result{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
