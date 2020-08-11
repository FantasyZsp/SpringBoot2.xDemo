package xyz.mydev.common.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ZSP
 */
public interface IBasedLevelTree<L, T> {

  /**
   * 获取结点所处level
   * 主要作为划分、获取同一结点下对应的结点用。
   * {@link Collectors#groupingBy(java.util.function.Function)}将以此level作为划分依据。
   * level一般是有规律的，或者可以由父结点信息推算出子结点的level的，比如子结点拥有父结点的id时或者level的设计本身具有累加推导性。
   * <p>
   * 不要使用集合类型作为level。
   * 一般使用数字和字符串。
   *
   * @return 结点所处level
   */
  L level();

  /**
   * 推算孩子结点所处的level
   *
   * @return 孩子结点所处level
   */
  L calculateChildLevel();

  /**
   * 设置孩子节点
   *
   * @param children 属于当前结点的孩子结点
   */
  void setChildren(List<T> children);

  /**
   * 获取孩子节点
   *
   * @return 属于当前结点的孩子结点
   */
  List<T> getChildren();

  /**
   * 亲戚结点排序
   *
   * @return 顺序优先级，越小优先级越高
   */
  int order();


  /**
   * 将list转为tree结构
   *
   * @param levelItems      需要转换的集合。可能是树，也可能是平级森林，即必须含有且仅含有一个相同的顶级level.
   * @param levelComparator 从levelItems找到根节点level的比较器
   * @return 已分组结构。
   */
  static <L, T extends IBasedLevelTree<L, T>> List<T> transfer2Tree(final List<T> levelItems,
                                                                    final Comparator<L> levelComparator) {

    Objects.requireNonNull(levelItems);
    Objects.requireNonNull(levelComparator);
    if (levelItems.isEmpty()) {
      return new ArrayList<>(0);
    }

    L rootLevel = levelItems.stream().map(T::level).min(levelComparator).orElseThrow();

    return transfer2Tree(rootLevel, levelItems);
  }

  /**
   * 将list转为tree结构
   *
   * @param rootLevel  根节点level，或森林顶层相同的level
   * @param levelItems 需要转换的集合。可能是树，也可能是平级森林，即必须含有且仅含有一个相同的顶级level.
   * @return 已分组结构。
   */
  static <L, T extends IBasedLevelTree<L, T>> List<T> transfer2Tree(L rootLevel,
                                                                    final List<T> levelItems) {

    if (invalidRootLevel(rootLevel)) {
      throw new IllegalArgumentException();
    }

    Objects.requireNonNull(levelItems);

    if (levelItems.isEmpty()) {
      return new ArrayList<>(0);
    }

    Map<L, List<T>> levelGroups = levelItems.stream().collect(Collectors.groupingBy(IBasedLevelTree::level));

    return recur2Tree(levelGroups.get(rootLevel), levelGroups);
  }


  /**
   * 递归的构造树形结构
   *
   * @param currentLevelGroup 某一层级所有兄弟结点
   * @param levelGroups       同一森林中从某层级后所有items
   * @return 构造好的树形结构。可能是树，也可能是森林，取决于传入数据的特性
   */
  static <L, T extends IBasedLevelTree<L, T>> List<T> recur2Tree(final List<T> currentLevelGroup, final Map<L, List<T>> levelGroups) {

    if (currentLevelGroup == null || currentLevelGroup.isEmpty()) {
      return new ArrayList<>(0);
    }

    for (T item : currentLevelGroup) {

      L childLevel = item.calculateChildLevel();

      List<T> childLevelGroup = levelGroups.get(childLevel);

      if (childLevelGroup != null && !childLevelGroup.isEmpty()) {

        childLevelGroup.sort(Comparator.comparingInt(T::order));
        item.setChildren(childLevelGroup);
        recur2Tree(childLevelGroup, levelGroups);
      }

    }
    return currentLevelGroup;
  }


  static <L, T extends IBasedLevelTree<L, T>> List<T> tree2List(final T root) {

    Objects.requireNonNull(root);

    List<T> resultList = new ArrayList<>();
    resultList.add(root);

    List<T> originalChildren = root.getChildren();
    if (originalChildren == null) {
      return resultList;
    }

    List<T> children = new ArrayList<>(originalChildren);

    if (children.isEmpty()) {
      return resultList;
    }

    recur2List(resultList, children);

    for (T t : resultList) {
      t.setChildren(new ArrayList<>());
    }

    return resultList;
  }

  private static <L, T extends IBasedLevelTree<L, T>> void recur2List(List<T> resultList, List<T> children) {
    if (children != null && !children.isEmpty()) {
      for (T child : children) {
        if (child != null) {
          resultList.add(child);
          recur2List(resultList, child.getChildren());
        }
      }
    }


  }

  /**
   * 是否是有效结点level
   *
   * @param rootLevel 结点level
   * @return 无效时返回true
   */
  private static <L> boolean invalidRootLevel(L rootLevel) {
    return rootLevel == null || (rootLevel instanceof String && ((String) rootLevel).length() == 0);
  }
}
