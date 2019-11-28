package xyz.mydev.jdk.lanmbda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author  ZSP
 * @date  2018/10/15 16:25
 * @description
 */
@FunctionalInterface
interface MyInterface1<T> {
  String printUpperCase(T str);

  static void print(String str) {
    System.out.print(str + " ");
  }
}

interface MyInterface2 {
  void printUpperCase();
}

@FunctionalInterface
interface MyInterface3 {
  String printUpperCase(Test2 test2);
}

class MyInterface1Impl implements MyInterface1<String> {
  @Override
  public String printUpperCase(String str) {
    return str + "interface1";
  }

  public static String myStr(String str) {
    return str + "XXX";
  }

  public String myStr2(String str) {
    return str + "XXX22";
  }
}


public class LambdaTest {
  public static void main(String[] args) {
    List<String> strings = Arrays.asList("this", "is", "an", "apple");
    strings.forEach(MyInterface1::print);

    ArrayList<String> arrayList = new ArrayList(strings);
    arrayList.add("FFF");


    System.out.println("\n=========");
    strings.forEach((s -> System.out.println(s.toUpperCase())));

    System.out.println("\n=========");
    String testSSS = "ssss";
    testSSS.toUpperCase();
    System.out.printf("调用方法后并没有更改调用者[testSSS=%s] 的值，而是返回了新的字符串实例[testSSS=%s] \n", testSSS, testSSS.toUpperCase());

    System.out.println("\n=========");
    strings.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.print(s + " "));
    strings.stream().map(String::toUpperCase).forEach(s -> System.out.print(s + " "));

    System.out.println("\n=========");
    strings.stream().map(MyInterface1Impl::myStr).forEach(s -> System.out.print(s + " "));

    System.out.println("\n=========");
    strings.stream().map((s) -> (s + "1")).forEach(s -> System.out.print(s + " "));


    System.out.println("\n=========");
    System.out.println(strings);

    Test2 test2 = new Test2();
    Test2 test3 = new Test2();
    Test2 test4 = new Test2();
    Test2 test5 = new Test2();
    test2.printUpperCase(() -> System.out.println("参数是函数接口的时候，可以直接用lambda实现接口"));
    strings.stream().map(test2::myStr22).forEach(s -> System.out.print(s + " "));

//        strings.stream().map(s->s.myStr22).forEach(s -> System.out.print(s + " "));
    List<Test2> list = Arrays.asList(test2, test3, test4, test5);
    System.out.println("\n======11===");
    list.stream().map(Test2::myStr33).forEach(System.out::println);
    System.out.println("\n======11===");

    // Test2必须是要实现的接口的入参
//        MyInterface3 interface3 = Test2::myStr33;
//        System.out.println(new TestMyInterface3().test(interface3));
//        System.out.println(new TestMyInterface3().test(interface3));
//        System.out.println(new TestMyInterface3().test(interface3));
//        System.out.println(new TestMyInterface3().test(interface3));


  }

}

class TestMyInterface3 {

  public String test(MyInterface3 interface3) {
    return interface3.printUpperCase(new Test2());
  }
}
