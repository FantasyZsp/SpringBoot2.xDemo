package xyz.mydev.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mydev.stream.bean.VesselContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZSP
 * @date 2018/11/05 21:03
 * @description 分组
 */
public class Grouping {
  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    VesselContainer c0 = new VesselContainer("20154221", "AAAA1111111", "AAAAXXXXXXXX", 4649, "未理货");
    VesselContainer c1 = new VesselContainer("20154221", "AAAA1111111", "AAAAYYYYYYYY", 4649, "未理货");
    VesselContainer c2 = new VesselContainer("20154221", "AAAA1111111", "AAAAZZZZZZZZ", 4650, "未理货");

    VesselContainer c3 = new VesselContainer("20154221", "AAAA2222222", "AAAAWWWWWWWW", 4648, "未理货");
    VesselContainer c7 = new VesselContainer("20154221", "AAAA2222222", "AAAABBBBBBBB", 4655, "未理货");
    VesselContainer c8 = new VesselContainer("20154221", "AAAA2222222", "AAAAWU8600ZZ", 4656, "未理货");

    VesselContainer c9 = new VesselContainer("20154221", "BBBB9931780", "BBBBWU8600XX", 4657, "未理货");
    VesselContainer c6 = new VesselContainer("20154221", "BBBB1691678", "BBBBWU8600DC", 4654, "未理货");
    VesselContainer c4 = new VesselContainer("20154221", "BBBB2543612", "BBBBWU8600DA", 4652, "未理货");
    VesselContainer c5 = new VesselContainer("20154221", "BBBB9932298", "BBBBWU8600DB", 4653, "未理货");

    List<VesselContainer> containers = Arrays.asList(c0, c1, c2, c3, c4, c5, c6, c7, c8, c9);
//        List<VesselContainer> containers = VesselContainer.generateContainers(1000);
    // 按箱号分组
    Map<String, List<VesselContainer>> collectionMap = containers.stream().collect(Collectors.groupingBy(VesselContainer::getContainerNumber));
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collectionMap));
    System.out.println("======");
    // 同组同号,合并数据 1、重量 2、单号
    List<VesselContainer> mergeResults = new ArrayList<>();
    collectionMap.forEach(
      (containerNo, containerBean) -> {
        if (containerBean.size() > 1) {
//                        containerBean.sort(Comparator.comparingInt(VesselContainer::getWeight).reversed());
          containerBean.get(0).setWeight(containerBean.stream().mapToInt(VesselContainer::getWeight).reduce(0, Integer::sum));
          containerBean.get(0).setBlNumber(containerBean.stream().map(VesselContainer::getBlNumber).collect(Collectors.joining(",")));

        }
//                    containerBean.stream().distinct().findFirst().ifPresent(mergeResults::add);
        mergeResults.add(containerBean.get(0));
      }
    );
    mergeResults.sort(Comparator.comparingInt(VesselContainer::getWeight));
    mergeResults.forEach(System.out::println);

    System.out.println("======");

    Map<String, Long> collect1 = containers.stream().collect(Collectors.groupingBy(VesselContainer::getContainerNumber, Collectors.counting()));
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collect1));


  }
}

