package xyz.mydev.jackson.relationship.m2m;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao 2018/09/12 14:22
 */
@Entity
@Getter
@Setter
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String stuName;
  private Integer stuAge;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Teacher> teachers = new ArrayList<>();

  public Student() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    if (!id.equals(student.id)) return false;
    return stuName.equals(student.stuName);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + stuName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Student{" +
      "id=" + id +
      ", stuName='" + stuName + '\'' +
      ", stuAge=" + stuAge +
      ", teachers=" + teachers +
      '}';
  }
}
