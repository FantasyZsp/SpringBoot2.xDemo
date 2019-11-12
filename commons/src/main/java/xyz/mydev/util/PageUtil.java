package xyz.mydev.util;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author ZSP
 */
public class PageUtil {
  /**
   * 返回分页后结果
   *
   * @param page 当前页，从1开始
   * @param size 每页大小
   * @return 分页后结果
   * @author ZSP
   */
  public static <T> List<T> paging(List<T> pagedList, int page, int size) {
    if (pagedList == null) {
      return Collections.emptyList();
    }
    if (page < 1) {
      page = 1;
    }
    page = page - 1;
    long skipCount = (long) (page * size);
    return pagedList.stream().skip(skipCount).limit(size).collect(toList());
  }
}
