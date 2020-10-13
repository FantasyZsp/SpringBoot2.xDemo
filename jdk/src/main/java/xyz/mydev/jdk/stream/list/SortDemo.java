package xyz.mydev.jdk.stream.list;

import xyz.mydev.jdk.bean.IdTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author ZSP
 */
public class SortDemo {

  public static List<IdTimeEntity> init() {
    LocalDateTime now = LocalDateTime.now();
    return List.of(
      new IdTimeEntity("1", now),
      new IdTimeEntity("2", now.plusDays(1L)),
      new IdTimeEntity("2", now.plusDays(2L)),
      new IdTimeEntity("3", now.plusDays(2L)),
      new IdTimeEntity("3", now.plusDays(3L)),
      new IdTimeEntity("4", now.plusDays(3L))
    );
  }

  public static void main(String[] args) {
    List<IdTimeEntity> data = new ArrayList<>(init());
    data.sort(Comparator.comparing(IdTimeEntity::getId).reversed().thenComparing(Comparator.comparing(IdTimeEntity::getStartTime).reversed()));
    System.out.println(data);
  }

}
