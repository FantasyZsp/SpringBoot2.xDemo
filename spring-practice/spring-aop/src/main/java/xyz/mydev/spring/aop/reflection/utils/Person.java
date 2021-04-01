package xyz.mydev.spring.aop.reflection.utils;

import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ZSP
 */

@Data
public class Person {

  String name;
  String id;

  public void eat() {
    System.out.println("eat");
  }

  public void check() throws RuntimeException{
    if(ThreadLocalRandom.current().nextBoolean()){
      throw new RuntimeException();
    }
  }

}
