package xyz.mydev.utils.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ZSP
 */
public interface IBasedLevelTree {

  String getParentId();

  String getLevel();

  String getChildLevel();

  List<? extends IBasedLevelTree> getChildren();

  void setChildren(List<? extends IBasedLevelTree> children);

  int getOrder();


  /**
   * 将list转为tree结构
   *
   * @param rootLevel  当前根节点
   * @param levelItems 需要转换的集合。可能是所有用户组，也可能是从某层级开始往下的用户组。要求必须含有且仅含有一个共同的起始节点.
   * @return 已分组结构。
   */
  static <T extends IBasedLevelTree> List<T> transfer2Tree(String rootLevel, final List<T> levelItems) {
    return transfer2Tree(rootLevel, levelItems, (list) ->
      list.stream().map(T::getLevel).sorted().distinct().min(Comparator.comparingInt(String::length)).orElseThrow());
  }

  /**
   * 将list转为tree结构
   *
   * @param rootLevel      当前根节点
   * @param levelItems     需要转换的集合。可能是所有用户组，也可能是从某层级开始往下的用户组。要求必须含有且仅含有一个共同的起始节点.
   * @param minLevelMapper 从levelItems找到rootLevel的映射器，当rootLevel无效时使用.
   * @return 已分组结构。
   */
  static <T extends IBasedLevelTree> List<T> transfer2Tree(String rootLevel, final List<T> levelItems, final Function<List<T>, String> minLevelMapper) {
    if (levelItems == null || levelItems.isEmpty()) {
      return new ArrayList<>(0);
    }

    Map<String, List<T>> levelGroups = levelItems.stream().collect(Collectors.groupingBy(IBasedLevelTree::getLevel));
    if (rootLevel == null || rootLevel.length() == 0) {

      if (minLevelMapper == null) {
        // 拿到父节点level
        rootLevel = levelItems.stream().map(T::getLevel).sorted().distinct().min(Comparator.comparingInt(String::length)).orElseThrow();
      } else {
        rootLevel = minLevelMapper.apply(levelItems);
      }
    }

    Objects.requireNonNull(rootLevel);
    return recur2Tree(levelGroups.get(rootLevel), levelGroups);
  }

  private static <T extends IBasedLevelTree> List<T> recur2Tree(final List<T> currentLevelGroup, final Map<String, List<T>> levelGroups) {
    if (currentLevelGroup == null || currentLevelGroup.isEmpty()) {
      return new ArrayList<>(0);
    }
    currentLevelGroup.forEach((T item) -> {
      String childLevel = item.getChildLevel();
      List<T> childLevelGroup = levelGroups.get(childLevel);
      if (childLevelGroup != null && !childLevelGroup.isEmpty()) {
        // 排序
        childLevelGroup.sort(Comparator.comparingInt(T::getOrder));
        item.setChildren(childLevelGroup);
        recur2Tree(childLevelGroup, levelGroups);
      }
    });
    return currentLevelGroup;
  }
}
