package xyz.mydev.util;

import xyz.mydev.datastructure.set.User;

import java.util.Comparator;

/**
 * @author  zhao
 * @date  2018/09/06 17:27
 * @description
 */
public class MyComparator {
  public static Comparator<User> sortByIdASC() {
    Comparator c = new Comparator<User>() {
      @Override
      public int compare(User o1, User o2) {
        if (o1.getId() > o2.getId()) {
          return 1;
        } else if (o1.getId() == o2.getId()) {
          return 0;
        } else {
          return -1;
        }
      }
    };
    return c;
  }
}
