package xyz.mydev.jdk.genericity.bean;

import lombok.Getter;

/**
 * @author ZSP
 */
@Getter
public class Animal implements Base {

  String id;
  String subject;

  public void run() {
    System.out.println("run");
  }

  public void eat() {
    System.out.println("eat");

  }

}
