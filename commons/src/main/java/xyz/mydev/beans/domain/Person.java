package xyz.mydev.beans.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class Person implements Serializable {
  private Long id;
  private String name;
  private String address;

  private Instant birthday;

  public Person() {
  }

  public Person(Long id, String name, String address, Instant birthday) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.birthday = birthday;
  }
}
