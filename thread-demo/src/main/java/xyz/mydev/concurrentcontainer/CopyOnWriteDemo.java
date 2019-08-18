package xyz.mydev.concurrentcontainer;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteDemo {

  private final CopyOnWriteArrayList<String> list
    = new CopyOnWriteArrayList<>(Arrays.asList("1", "2", "3", "4"));

  public void testCOWList() {
    ListIterator<String> stringListIterator = list.listIterator();
    while (stringListIterator.hasNext()) {
      System.out.println(stringListIterator.next());
    }
  }


  public static void main(String[] args) {
    CopyOnWriteDemo demo = new CopyOnWriteDemo();
    demo.testCOWList();

  }

}
