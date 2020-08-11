package xyz.mydev.common.utils;

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
public class LinkedEntityUtil {
  /**
   * 首结点 preId
   */
  public static final String ROOT_PRE = "root_pre";

  private static List<LinkedEntity> sort(List<? extends LinkedEntity> unorderedList) {
    if (CollectionUtils.isEmpty(unorderedList)) {
      return Collections.emptyList();
    }

    int size = unorderedList.size();
    // id->entity
    Map<String, LinkedEntity> entityMap = new HashMap<>(size);

    for (LinkedEntity config : unorderedList) {
      entityMap.put(config.getPreId(), config);
    }

    LinkedEntity root = entityMap.get(ROOT_PRE);
    Assert.isTrue(root != null, "数据异常！找不到首节点");

    List<LinkedEntity> sortedList = new ArrayList<>(size);
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
  public interface LinkedEntity {


    /**
     * 获取结点id
     *
     * @return 结点id
     * @author ZSP
     */
    String getId();

    /**
     * 获取前驱结点id
     *
     * @return 前驱结点id，当首结点时为 root_pre
     * @author ZSP
     */
    String getPreId();
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class DemoEntity implements LinkedEntity {
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
    DemoEntity root = new DemoEntity("1", ROOT_PRE);
    DemoEntity second = new DemoEntity("2", "1");
    DemoEntity third = new DemoEntity("3", "2");
    DemoEntity forth = new DemoEntity("4", "3");

    List<DemoEntity> unorderedList = new ArrayList<>();
    unorderedList.add(third);
    unorderedList.add(root);
    unorderedList.add(forth);
    unorderedList.add(second);

    List<LinkedEntity> linkedEntities = sort(unorderedList);
    System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(linkedEntities));
  }
}
