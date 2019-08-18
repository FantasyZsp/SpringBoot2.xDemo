package xyz.mydev.stream.service;

import xyz.mydev.stream.bean.VesselContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZSP
 * @date 2018/11/06 19:55
 * @description
 */
public class MergeService {
  public List<VesselContainer> merge(List<VesselContainer> source, List<VesselContainer> target) {

//        source.add()
    return null;

  }

  public static void main(String[] args) {
    // 源数据
    VesselContainer a1 = new VesselContainer("20154221", "AAAA1111111", "AAAAXXXXXXXX1", 5000, "未理货");
    VesselContainer a2 = new VesselContainer("20154221", "AAAA2222222", "AAAAYYYYYYYY", 5000, "未理货");
    VesselContainer a3 = new VesselContainer("20154221", "AAAA3333333", "AAAAZZZZZZZZ", 5000, "未理货");

    VesselContainer a4 = new VesselContainer("20154221", "AAAA4444444", "AAAAWWWWWWWW", 5000, "未理货");
    // 注释掉为删除
    VesselContainer a5 = new VesselContainer("20154221", "AAAA5555555", "AAAABBBBBBBB", 5000, "未理货");
    // 新增
    VesselContainer a6 = new VesselContainer("20154221", "AAAA5555555", "AAAAWU8600ZZ", 5000, "未理货");

    List<VesselContainer> sources = Arrays.asList(a1, a2, a3, a4, a6);

    // 本地数据
    VesselContainer b1 = new VesselContainer("20154221", "AAAA1111111", "AAAAXXXXXXXX", 5000, "已理货");
    VesselContainer b2 = new VesselContainer("20154221", "AAAA2222222", "AAAAYYYYYYYY", 5000, "已理货");
    VesselContainer b3 = new VesselContainer("20154221", "AAAA3333333", "AAAAZZZZZZZZ", 5000, "已理货");

    VesselContainer b4 = new VesselContainer("20154221", "AAAA4444444", "AAAAWWWWWWWW", 5000, "未理货");
    VesselContainer b5 = new VesselContainer("20154221", "AAAA5555555", "AAAABBBBBBBB", 5000, "未理货");
    List<VesselContainer> targets = Arrays.asList(b1, b2, b3, b4, b5);

    // 差集1: sources - targets,得到新增数据
    System.out.println("=======新增数据=========");
    List<VesselContainer> containersNew = sources.stream().filter(item -> !targets.contains(item)).collect(Collectors.toList());
    containersNew.forEach(System.out::println);
    System.out.println("================");

    // 差集2: targets - sources ,得到删除数据
    System.out.println("=======删除数据=========");
    List<VesselContainer> containersDeleted = targets.stream().filter(item -> !sources.contains(item)).collect(Collectors.toList());
    containersDeleted.forEach(System.out::println);

    // 交集,得到可能更新数据
    System.out.println("=======可能更新数据=========");
    List<VesselContainer> containersUpdate = targets.stream().filter(item -> !item.getContainerStatus().equals("已理货")).filter(sources::contains).collect(Collectors.toList());
    containersUpdate.forEach(System.out::println);

    // 处理是否需要更新

  }


}
