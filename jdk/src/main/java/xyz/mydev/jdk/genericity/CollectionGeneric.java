package xyz.mydev.jdk.genericity;

import xyz.mydev.jdk.genericity.bean.Animal;
import xyz.mydev.jdk.genericity.bean.Cat;
import xyz.mydev.jdk.genericity.bean.Dog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
@SuppressWarnings("all")
public class CollectionGeneric {


  private static List<Animal> animals = new ArrayList<>();
  private static List<? super Animal> animalsSu = new ArrayList<>();
  private static List<? extends Animal> animalsEx = new ArrayList<>();

  private static List<Dog> dogs = new ArrayList<>();
  private static List<? super Dog> dogsSu = new ArrayList<>();
  private static List<? extends Dog> dogsEx = new ArrayList<>();

  private static List<Cat> cats = new ArrayList<>();
  private static List<? super Cat> catsSu = new ArrayList<>();
  private static List<? extends Dog> catsEx = new ArrayList<>();


  public static void main(String[] args) {

    Cat cat = new Cat();
//    dogs.add(cat);  /*Required type:Dog Provided:Cat*/
//    dogsSu.add(cat); /*Required type: capture of ? super Dog Provided: Cat*/

//    dogsEx.add(cat); /*Required type:capture of ? extends Dog Provided: Cat*/

    Dog dog = new Dog();
    dogs.add(dog);
    dogsSu.add(dog);
//    dogsEx.add(dog); /*Required type: capture of ? extends Dog Provided: Dog*/


    animals.add(dog);
    animalsSu.add(dog);
//    animalsEx.add(dog); /*Required type: capture of ? extends Animal Provided: Dog*/

    Animal animal = new Animal();
//    dogs.add(animal);
//    dogsSu.add(animal);
//    dogsEx.add(animal);


    // get

    Object object = dogsSu.get(1);
    Dog dog1 = dogsEx.get(1);

  }

  interface Container<T> {

    void add(T t);

    T get();

  }


}
