package xyz.mydev.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.mydev.util.JsonUtil;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author ZSP
 */

public class MultiMapDemo {


  private static Multimap<String, NameBean> initArrayListMultiMap() {
    Multimap<String, NameBean> multiMap = ArrayListMultimap.create();
    multiMap.put("1", NameBean.builder().name("1").status("1").build());
    multiMap.put("2", NameBean.builder().name("2").status("2").build());
    multiMap.put("3", NameBean.builder().name("3").status("3").build());
    multiMap.put("4", NameBean.builder().name("4").status("4").build());
    multiMap.put("4", NameBean.builder().name("4same").status("4").build());
    multiMap.put("4", NameBean.builder().name("4same").status("4").build());
    return multiMap;
  }

  private static Multimap<String, NameBean> initHashMultiMap() {
    Multimap<String, NameBean> multiMap = HashMultimap.create();
    multiMap.put("1", NameBean.builder().name("1").status("1").test("test1").build());
    multiMap.put("2", NameBean.builder().name("2").status("2").test("test2").build());

    multiMap.put("1", NameBean.builder().name("4same").status("4").build());
    multiMap.put("1", NameBean.builder().name("4same").status("4").build());
    return multiMap;
  }


  public static void main(String[] args) {
    Multimap<String, NameBean> init = initHashMultiMap();
    System.out.println(init);
    print(init.asMap());
    Collection<NameBean> multiMapDemos = init.get("4");
    print(multiMapDemos);


    HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
    objectObjectHashMap.put(1, 2);
    System.out.println(objectObjectHashMap.get(null));

  }

  private static <T> void print(T t) {
    System.out.println(JsonUtil.obj2StringPretty(t));
  }

  @NoArgsConstructor
  @Getter
  @Setter
  @AllArgsConstructor
  @Builder
  @Data
  private static class NameBean {
    private String name;
    private String status;
    private String test;
  }
}
