package xyz.mydev.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZSP
 */
public class LinkedEntityUtilV2 {
  /**
   * 首结点 preId
   */
  public static final String ROOT_PRE = "root_pre";

  private static List<LinkedEntityV2> sort(List<? extends LinkedEntityV2> unorderedList) {
    if (CollectionUtils.isEmpty(unorderedList)) {
      return Collections.emptyList();
    }

    int size = unorderedList.size();
    // id->entity
    Map<String, LinkedEntityV2> entityMap = new HashMap<>(size);

    for (LinkedEntityV2 config : unorderedList) {
      entityMap.put(config.getPreId(), config);
    }

    LinkedEntityV2 root = entityMap.get(ROOT_PRE);
    Assert.isTrue(root != null, "数据异常！找不到首节点");

    List<LinkedEntityV2> sortedList = new ArrayList<>(size);
    sortedList.add(root);
    // 添加root后继结点
    while ((root = entityMap.get(root.getId())) != null) {
      sortedList.add(root);
    }
    return sortedList;
  }

  /**
   * 含有链表性质的实体父类
   *
   * @author ZSP
   */
  public static abstract class LinkedEntityV2 {


    /**
     * 获取结点id
     *
     * @return 结点id
     * @author ZSP
     */
    abstract String getId();

    /**
     * 获取前驱结点id
     *
     * @return 前驱结点id，当首结点时为 root_pre
     * @author ZSP
     */
    abstract String getPreId();
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DemoEntityV2 extends LinkedEntityV2 {
    private String id;
    private String preId;

    @Override
    public String toString() {
      return "DemoEntity{" +
        "id='" + id + '\'' +
        ", preId='" + preId + '\'' +
        '}';
    }
  }

  public static void main(String[] args) throws JsonProcessingException {
    DemoEntityV2 root = new DemoEntityV2("1", ROOT_PRE);
    DemoEntityV2 second = new DemoEntityV2("2", "1");
    DemoEntityV2 third = new DemoEntityV2("3", "2");
    DemoEntityV2 forth = new DemoEntityV2("4", "3");

    List<DemoEntityV2> unorderedList = new ArrayList<>();
    unorderedList.add(third);
    unorderedList.add(root);
    unorderedList.add(forth);
    unorderedList.add(second);

    List linkedEntities = sort(unorderedList);
    System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(linkedEntities));
  }
}
