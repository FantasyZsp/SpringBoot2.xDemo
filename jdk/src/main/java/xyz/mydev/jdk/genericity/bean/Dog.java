package xyz.mydev.jdk.genericity.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZSP
 */
@Getter
@Setter
public class Dog extends Animal {

  private String id;
  private String subject;
  private String dogName;

  @Override
  public void run() {
    System.out.println("dog runs fast");
  }

  @Override
  public void eat() {
    System.out.println("dog eats much");

  }
}
