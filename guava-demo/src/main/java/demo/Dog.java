package demo;

/**
 * @author zsp
 */
public class Dog extends Animal {

  public Dog() {

  }

  public Dog(String name) {
    super(name);
  }

  @Override
  public void move() {
    System.out.println("I can run with my four legs.");
  }

  @Override
  public void self() {
    System.out.println("My name is " + getName());
  }


}
