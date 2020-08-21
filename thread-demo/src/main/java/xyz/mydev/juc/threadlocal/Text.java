package xyz.mydev.juc.threadlocal;

import lombok.Setter;

/**
 * @author zhaosp
 */
@Setter
public class Text {
  public String name;

  public Text(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name + "@" + Integer.toHexString(hashCode());
  }
}