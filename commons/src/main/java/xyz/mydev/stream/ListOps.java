package xyz.mydev.stream;

import xyz.mydev.mapstruct.domain.Person;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ZSP
 * @date 2018/11/05 16:16
 * @description
 */
public class ListOps {
  public static void main(String[] args) {
    Stream<String> strStream = Stream.of("this ", "is ", "a ", "demo");


//        String[] str = strStream.toArray(String[]::new);
//        Arrays.asList(str).forEach(System.out::println);

//        List<String> list = strStream.collect(Collectors.toList());

//        List<String> list = strStream.collect(ArrayList::new, List::add, List::addAll);
//        HashSet<String> list = strStream.collect(Collectors.toCollection(HashSet::new));
    TreeSet<String> list = strStream.collect(Collectors.toCollection(TreeSet::new));
    list.forEach(System.out::println);

    long time1 = System.currentTimeMillis();
    List<Integer> intList = IntStream.rangeClosed(1, 3_000_000).collect(ArrayList::new, List::add, List::addAll);
    System.out.println(System.currentTimeMillis() - time1);
    System.out.println(intList.size());

//        List<Integer> intList2 = IntStream.rangeClosed(1, 3_000_000).collect(ArrayList::new, List::add, List::addAll);

    System.out.println("字符串拼接");
    Stream<String> strStream2 = Stream.of("this ", "is ", "a ", "demo");
    String concat = strStream2.collect(Collectors.joining(",", "<", ">"));
    System.out.println(concat);


    List<String> list1 = Arrays.asList("Hi", "Hello", "你好");
    List<String> list2 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");
    list1.forEach(item -> list2.forEach(item2 -> System.out.println(item + " " + item2)));
    list1.stream().flatMap(item -> list2.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);

    String spliter = ",";
    String[] split = spliter.split(",");
    System.out.println(split.toString());

    List<String> listSSS = new ArrayList<>();
    System.out.println(listSSS);

    List<Person> list3 = new ArrayList<>();
    Person person = new Person(1L, "name", "address", Instant.now());
    list3.add(person);
    list3.add(new Person(1L, "name", "address", Instant.now()));
    list3.add(new Person(1L, "name", "address", Instant.now()));
    person.setId(2L);
    System.out.println(list3);
  }

}
