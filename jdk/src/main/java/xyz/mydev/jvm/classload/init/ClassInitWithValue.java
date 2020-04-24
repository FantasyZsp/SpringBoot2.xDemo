package xyz.mydev.jvm.classload.init;

/**
 * 一、注意默认值和明确赋值默认值的影响不同。
 * 二、初始化时，static变量按编写顺序被执行初始化，所以相对顺序很重要。
 *
 * @author ZSP
 */
public class ClassInitWithValue {

  public static void main(String[] args) {
    System.out.println(Singleton.getSingleton());
    System.out.println("main");
    System.out.println(Singleton.counter);
    System.out.println(Singleton.counter1);
    System.out.println(Singleton.counter2);
    System.out.println(Singleton.counter3);
  }
}

class Singleton {
  public static int counter;

  static {
    System.out.println("first static invoke");
    System.out.println(counter);
    System.out.println("first static invoke end ");

  }

  private static Singleton singleton = new Singleton();


  private Singleton() {
    System.out.println("constructor invoke");
    System.out.println(counter);
    System.out.println(counter1);
    System.out.println(counter2);
    System.out.println(counter3);
    counter++;
    counter1++;
    counter2++;
    counter3++;
    System.out.println(counter);
    System.out.println(counter1);
    System.out.println(counter2);
    System.out.println(counter3);
    System.out.println("constructor end");
  }

  public static int counter1;
  /**
   * 与 counter1 的区别仅在于，1没有明显的赋值，所以在初始化阶段被跳过了，而2 因为 明确的赋值，在此阶段会赋值为0
   */
  public static int counter2 = 0;
  public static int counter3 = 3;


  static {
    System.out.println("static invoke");
    System.out.println(counter);
    System.out.println(counter1);
    System.out.println(counter2);
    System.out.println(counter3);
    counter++;
    counter1++;
    counter2++;
    counter3++;
    System.out.println(counter);
    System.out.println(counter1);
    System.out.println(counter2);
    System.out.println(counter3);
    System.out.println("static invoke end ");
  }

  public static Singleton getSingleton() {
    return singleton;
  }
}
