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
public class LinkedEntityUtilV3 {
  /**
   * 首结点 preId
   */
  public static final String ROOT_PRE = "root_pre";

  private static <T extends AbstractLinkedEntityV3> List<T> sort(List<T> unorderedList) {
    if (CollectionUtils.isEmpty(unorderedList)) {
      return Collections.emptyList();
    }

    int size = unorderedList.size();
    // id->entity
    Map<String, T> entityMap = new HashMap<>(size);

    for (T config : unorderedList) {
      entityMap.put(config.getPreId(), config);
    }

    T root = entityMap.get(ROOT_PRE);
    Assert.isTrue(root != null, "数据异常！找不到首节点");

    List<T> sortedList = new ArrayList<>(size);
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
  public interface AbstractLinkedEntityV3 {


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
  public static class ConcreteDemoEntityV3 implements AbstractLinkedEntityV3 {
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
    ConcreteDemoEntityV3 root = new ConcreteDemoEntityV3("1", ROOT_PRE);
    ConcreteDemoEntityV3 second = new ConcreteDemoEntityV3("2", "1");
    ConcreteDemoEntityV3 third = new ConcreteDemoEntityV3("3", "2");
    ConcreteDemoEntityV3 forth = new ConcreteDemoEntityV3("4", "3");

    List<ConcreteDemoEntityV3> unorderedList = new ArrayList<>();
    unorderedList.add(third);
    unorderedList.add(root);
    unorderedList.add(forth);
    unorderedList.add(second);

    List<ConcreteDemoEntityV3> linkedEntities = sort(unorderedList);
    System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(linkedEntities));
  }
}
