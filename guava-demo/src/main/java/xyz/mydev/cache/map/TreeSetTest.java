package xyz.mydev.cache.map;

import xyz.mydev.beans.DelayMsg;
import xyz.mydev.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author ZSP
 */
public class TreeSetTest {
  public static void main(String[] args) {

    TreeSet<DelayMsg> treeSet = new TreeSet<>(Comparator.comparing(DelayMsg::getTime));
//
    DelayMsg msg = new DelayMsg("id", LocalDateTime.now());
    DelayMsg msg2 = new DelayMsg("id2222", LocalDateTime.now());
    DelayMsg msg5 = new DelayMsg("id5555", LocalDateTime.now());
    DelayMsg msg3 = new DelayMsg("id3", LocalDateTime.now());
    DelayMsg msg4 = new DelayMsg("id4", LocalDateTime.now());

    treeSet.add(msg);
    treeSet.add(msg2);
    treeSet.add(msg3);
    treeSet.add(msg4);
    treeSet.add(msg5);

    System.out.println(treeSet.size());
    System.out.println(JsonUtil.obj2StringPretty(treeSet));


    HashSet<DelayMsg> hashSet = new HashSet<>();
    hashSet.add(msg);
    hashSet.add(msg2);
    hashSet.add(msg3);
    hashSet.add(msg4);
    hashSet.add(msg5);

    System.out.println(hashSet.size());
    System.out.println(JsonUtil.obj2StringPretty(hashSet));


  }

}
