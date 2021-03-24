package xyz.mydev.jdk.genericity.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZSP
 */
@Getter
@Setter
public class Cat extends Animal {

  private String id;
  private String subject;
  private String catName;


  @Override
  public void run() {
    System.out.println("cat runs fast");
  }

  @Override
  public void eat() {
    System.out.println("cat eats little");
  }
}
