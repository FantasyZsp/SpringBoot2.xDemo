package xyz.mydev.demo;

import java.util.ArrayList;
import java.util.List;

public class AnimalTest {

  public static void main(String[] args) {

    List<Animal> animals = init();

    animals.forEach(animal -> {
      animal.move();
      animal.self();
    });

  }

  private static void print(Object o) {
    System.out.println(o);
  }

  private static List<Animal> init() {
    Animal animal = new Animal("animal");
    System.out.println(animal.getClass().getSimpleName());
    System.out.println(animal);
    System.out.println(animal.getName());
    print("=========================");

    Animal dog = new Dog("dog");
    System.out.println(dog.getClass().getSimpleName());
    System.out.println(dog);
    System.out.println(dog.getName());


    print("=========================");
    Animal bird = new Bird("bird");
    System.out.println(bird.getClass().getSimpleName());
    System.out.println(bird);
    System.out.println(bird.getName());

    List<Animal> animals = new ArrayList<>();
    animals.add(animal);
    animals.add(dog);
    animals.add(bird);
    return animals;
  }
}
