package xyz.mydev.datastructure.set;

import xyz.mydev.util.MyComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhao  2018/08/13 12:13
 * @description
 */
public class SortSets {
  public static void main(String[] args) {
    User user = new User(1111, "User1");
    User user2 = new User(22222, "User2");
    User user3 = new User(33333, "User3");
    User user4 = new User(4444, "User4");
    User user5 = new User(55555, "User5");
    User user6 = new User(666666, "User6");
    Set<User> set = new HashSet<>();
    set.add(user6);
    set.add(user2);
    set.add(user);
    set.add(user3);
    set.add(user4);
    set.add(user5);

    System.out.println("================set================");
    System.out.println(set);
    for (User userx : set) {
      System.out.println(userx);
    }

    System.out.println();
    System.out.println("================list================");
    List<User> list = new ArrayList<>(set);
    System.out.println(list);
    System.out.println();
    for (User userx : list) {
      System.out.println(userx);
    }

    list.sort(MyComparator.sortByIdASC());
    System.out.println(list);
    for (User userx : list) {
      System.out.println(userx);
    }

  }
}
