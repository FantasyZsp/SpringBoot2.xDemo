package xyz.mydev.jvm.typeparam;

import com.google.common.collect.Lists;
import xyz.mydev.jvm.bean.Father;
import xyz.mydev.jvm.bean.Grandpa;
import xyz.mydev.jvm.bean.Son;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ? super T添加只能添加T和T子类，获取只能获取到Object，引用赋值只能赋值T及T父类.
 * ? extend T指的是T及T的子类中的一种，而不是任意种都可以，不能添加，因为不知道添加的具体类型是不是符合容器指定的某种类型。但可以获取，因为都是自身子类。
 * 方法调用的传参遵循引用赋值，是将参数传给形参。
 *
 * @author ZSP
 */
public class TestSuperFather {
  /**
   * 类方法
   */
  public static void testSuperFather(List<? super Father> t) {
    System.out.println(t);
  }


  /**
   * 测试方法
   * 测试引用赋值、添加约束、获取返回值
   */
  public static void testSuperFatherRefAssignTest() {

    // 只能添加本身或子类。不能添加父类
    List<? super Father> grandpas = new ArrayList<Grandpa>();
    grandpas.add(new Father());
    grandpas.add(new Son());  // super下界匹配符。因为虽然知道是Father的基类 但也不知道是往上数多少级的基类 只有Father以及它的派生类 编译器才能断定能触发多态
//    grandpas.add(new Grandpa()); // 添加父类，编译报错
//    grandpas.add(new Object());  // 添加父类，编译报错
    System.out.println(grandpas);


    // 引用赋值。不同于添加操作，可以赋值给具体类型是本身和父类的
    List<? super Father> objects = new ArrayList<Object>();
    List<? super Father> grandpaArrayList = new ArrayList<Grandpa>();
    List<? super Father> fathers = new ArrayList<Father>();

//     List<? super Father> sons = new ArrayList<Son>(); // 当具体类型是子类时，引用赋值报编译错误。 // TODO 为什么这里和添加行为相反

    // 通过类似的静态方法包装后，不报错。实际上是因为在Collections.singletonList后，泛型退化成Object，然后符合引用赋值。
    List<? super Father> sons2 = Collections.singletonList(new ArrayList<Son>());
    List<? super Father> arrayLists = Lists.newArrayList(new Son());

    // 只能读出一个Object
    Object object = grandpas.get(0);

    // 调用情况。

    // 具体类型调用
//    TypeParameterTest.testObjectList(grandpas);  // 不兼容具体类型
//    TypeParameterTest.testFatherList(grandpas);  // 不兼容具体类型,虽然是Father

    // super类下界限定符号
//    TypeParameterTest.testSuperGrandpa(grandpas);  // 编译报错，? super Father 不一定符合 ? super Grandpa
    TypeParameterTest.testSuperFather(grandpas);
    TypeParameterTest.testSuperSon(grandpas);

    // extends 类上界限定
  }

  /**
   * 测试方法
   * 测试引用赋值、添加约束、获取返回值
   */
  public static void testExtendsFatherRefAssignTest() {


  }


  public static void main(String[] args) {
    testSuperFatherRefAssignTest();
  }
}
