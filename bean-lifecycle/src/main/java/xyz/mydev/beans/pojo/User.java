package xyz.mydev.beans.pojo;

import lombok.Data;

/**
 * @author ZSP
 */
@Data
public class User {
  private String id;
  private String name;

  public User(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
