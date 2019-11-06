package demo;

/**
 * @author zsp
 */
public class Bird extends Animal {

  public Bird() {
  }

  public Bird(String name) {
    super(name);
  }

  @Override
  public void move() {
    System.out.println("I can fly with my wing.");
  }

  @Override
  public void self() {
    System.out.println("My name is " + getName());
  }


}
