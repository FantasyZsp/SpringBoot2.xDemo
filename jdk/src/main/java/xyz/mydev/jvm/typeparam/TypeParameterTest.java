package xyz.mydev.jvm.typeparam;


import xyz.mydev.jvm.bean.Father;
import xyz.mydev.jvm.bean.Grandpa;
import xyz.mydev.jvm.bean.Son;

import java.util.List;

/**
 * 泛型测试
 * 上界不可以添加，是因为编译期不知道是Target还是Target的派生类。
 * https://www.zhihu.com/question/20400700
 *
 * @author ZSP
 */
public class TypeParameterTest {

  public static void testObject(Object o) {
    System.out.println(o);
  }

  /**
   * Object List
   */
  public static void testObjectList(List<Object> objects) {
    System.out.println(objects);
  }


  public static void testGrandpaList(List<Grandpa> grandpas) {
    System.out.println(grandpas);
  }

  public static void testFatherList(List<Father> fathers) {
    System.out.println(fathers);
  }

  public static void testSonList(List<Son> sons) {
    System.out.println(sons);
  }

  /**
   * ? List
   */
  public static void testWildCardList(List<?> t) {
    System.out.println(t);
  }


  public static void testSuperObject(List<? super Object> t) {
    System.out.println(t);
  }

  public static void testExtendsObject(List<? extends Object> t) {
    System.out.println(t);
  }

  public static void testSuperGrandpa(List<? super Grandpa> t) {
    System.out.println(t);
  }

  public static void testExtendsGrandpa(List<? extends Grandpa> t) {
    System.out.println(t);
  }


  public static void testSuperFather(List<? super Father> t) {
    System.out.println(t);
  }

  public static void testExtendsFather(List<? extends Father> t) {
    System.out.println(t);
  }

  public static void testSuperSon(List<? super Son> t) {
    System.out.println(t);
  }

  public static void testExtendsSon(List<? extends Son> t) {
    System.out.println(t);
  }


  /**
   * T
   */
  public static <T> void testT(T t) {
    System.out.println(t);
  }

  public static <T> void testTypeList(List<T> t) {
    System.out.println(t);
  }


  public static <T extends Object> void testTypeExtendsObject(List<T> t) {
    System.out.println(t);
  }


  public static <T extends Grandpa> void testTypeExtendsGrandpa(List<T> t) {
    System.out.println(t);
  }


  public static <T extends Father> void testTypeExtendsFather(List<T> t) {
    System.out.println(t);
  }


  public static <T extends Son> void testTypeExtendsSon(List<T> t) {
    System.out.println(t);
  }


  public static void main(String[] args) {

  }
}
