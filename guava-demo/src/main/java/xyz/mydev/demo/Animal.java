package xyz.mydev.demo;

import lombok.ToString;

/**
 * @author zsp
 */
@ToString
public class Animal {
  private String name;

  public Animal() {
  }

  public Animal(String name) {
    this.name = name;
  }

  public void move() {
    System.out.println("I can move");
  }

  public void self() {
    System.out.println("My name is ...");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
