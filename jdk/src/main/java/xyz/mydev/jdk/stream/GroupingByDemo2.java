package xyz.mydev.jdk.stream;

import com.google.common.collect.Lists;
import org.junit.Test;
import xyz.mydev.common.utils.JsonUtil;
import xyz.mydev.jdk.bean.IdTimeEntity;
import xyz.mydev.jdk.bean.SimpleEntity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * GroupingBy要求key非空
 *
 * @author ZSP
 */
@SuppressWarnings("all")
public class GroupingByDemo2 {
  public static void main(String[] args) {

    test2();
  }

  public static void testGroupByNullKey(String[] args) {
    SimpleEntity simpleEntity = new SimpleEntity("1", false);
    SimpleEntity simpleEntity2 = new SimpleEntity("2", false);
    SimpleEntity simpleEntity13 = new SimpleEntity("1", true);
    SimpleEntity simpleEntityNull = new SimpleEntity(null, true);

    List<SimpleEntity> list = Lists.newArrayList(simpleEntity, simpleEntity2, simpleEntity13, simpleEntityNull);

    System.out.println(list.stream().map(SimpleEntity::getId).collect(Collectors.toSet()));


    HashMap<String, String> map = new HashMap<>();

    map.put(null, "nullValue");

    System.out.println(map.get(null));
    // 报错，GroupingBy要求key非空
    System.out.println(list.stream().collect(Collectors.groupingBy(SimpleEntity::getId)));

  }

  public static void test2() {
    LocalDateTime now = LocalDateTime.now();
    IdTimeEntity simpleEntity = new IdTimeEntity("1", now);
    IdTimeEntity simpleEntity2 = new IdTimeEntity("2", now.plusDays(1L));
    IdTimeEntity simpleEntity3 = new IdTimeEntity("3", now.plusDays(2L));
    IdTimeEntity simpleEntity4 = new IdTimeEntity("4", now.plusDays(3L));
    IdTimeEntity simpleEntity5 = new IdTimeEntity("1", now.plusDays(1));
    IdTimeEntity simpleEntity6 = new IdTimeEntity("1", now.plusDays(2));
    IdTimeEntity simpleEntity7 = new IdTimeEntity("1", now.plusDays(3));

    List<IdTimeEntity> records = Lists.newArrayList(simpleEntity, simpleEntity2, simpleEntity3, simpleEntity4, simpleEntity7, simpleEntity5, simpleEntity6);
    TreeMap<String, List<LocalDateTime>> treeMap = records.stream()

      .collect(Collectors.groupingBy(IdTimeEntity::getId, TreeMap::new, mapping(IdTimeEntity::getStartTime, toList())));

    List<LocalDateTime> targetTime = treeMap.get("1");

    targetTime.sort(Comparator.reverseOrder());

    System.out.println(JsonUtil.obj2StringPretty(treeMap));


    Object[] objects = records.stream().map(IdTimeEntity::getStartTime).toArray();
  }


  @Test
  public void test3() {
    LocalDateTime now = LocalDateTime.now();
    IdTimeEntity simpleEntity = new IdTimeEntity("1", now);
    IdTimeEntity simpleEntity2 = new IdTimeEntity("2", now.plusDays(1L));
    IdTimeEntity simpleEntity3 = new IdTimeEntity("3", now.plusDays(2L));
    IdTimeEntity simpleEntity4 = new IdTimeEntity("4", now.plusDays(3L));


    List<IdTimeEntity> records = Lists.newArrayList(simpleEntity4, simpleEntity3, simpleEntity2, simpleEntity);
    LinkedHashMap<String, List<LocalDateTime>> linked = records.stream().collect(Collectors.groupingBy(IdTimeEntity::getId, LinkedHashMap::new, mapping(IdTimeEntity::getStartTime, toList())));

    List<LocalDateTime> targetTime = linked.get("1");

    System.out.println(JsonUtil.obj2StringPretty(linked));
  }


}
