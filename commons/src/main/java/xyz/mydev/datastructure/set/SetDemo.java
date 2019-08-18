package xyz.mydev.datastructure.set;

import java.util.Set;

/**
 * @author zhao   2018/08/13 12:08
 */
public class SetDemo {
  private String id;
  private String name;
  private Set<Result> resultSet;

  public SetDemo() {
  }

  public SetDemo(String id, String name, Set<Result> resultSet) {
    this.id = id;
    this.name = name;
    this.resultSet = resultSet;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Result> getResultSet() {
    return resultSet;
  }

  public void setResultSet(Set<Result> resultSet) {
    this.resultSet = resultSet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SetDemo setDemo = (SetDemo) o;

    if (!id.equals(setDemo.id)) return false;
    if (!name.equals(setDemo.name)) return false;
    return resultSet.equals(setDemo.resultSet);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + resultSet.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SetDemo{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", resultSet=" + resultSet +
      '}';
  }
}
