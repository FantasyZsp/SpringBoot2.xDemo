package xyz.mydev.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author ZSP  2019/08/24 12:48
 */
public class SortAndPage {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws JsonProcessingException {
    List<Integer> collect = IntStream.rangeClosed(1, 100).boxed().collect(toList());
    printMsg("=========================origin=========================");
    printObject(collect);

    printMsg("=========================stream().sorted()=========================");
    printObject(collect.stream().sorted().collect(toList()));
    printMsg("=========================Integer::compareTo=========================");
    collect.sort(Integer::compareTo);
    printObject(collect);
    printMsg("=========================Integer::compareTo reserved=========================");
    Comparator<Integer> comparator = Integer::compareTo;
    collect.sort(comparator.reversed());
    printObject(collect);

    printMsg("=========================reserved limit 10 page 2=========================");
    printObject(collect.stream().skip(10).limit(10).collect(Collectors.toList()));

    collect.sort(comparator);
    printObject(collect.stream().skip(10).limit(10).collect(Collectors.toList()));


  }

  private static void printObject(Object object) throws JsonProcessingException {
    System.out.println(objectMapper.writeValueAsString(object));
  }

  private static void printMsg(String str) {
    System.out.println(str);
  }
}
