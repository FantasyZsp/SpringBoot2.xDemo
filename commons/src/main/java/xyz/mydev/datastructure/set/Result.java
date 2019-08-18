package xyz.mydev.datastructure.set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhao   2018/08/13 12:16
 * @description
 */
@Getter
@Setter
public class Result {
  private String id;
  private String name;

  public Result() {
  }

  public Result(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Result result = (Result) o;

    if (id != null ? !id.equals(result.id) : result.id != null) return false;
    return name != null ? name.equals(result.name) : result.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Result{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
