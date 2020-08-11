package xyz.mydev.datastructure.set;

import xyz.mydev.datastructure.set.User;

import java.util.Comparator;

/**
 * @author zhao
 * @date 2018/09/06 17:27
 */
public class MyComparator {
  public static Comparator<User> sortByIdASC() {
    return Comparator.comparingInt(User::getId);
  }
}
