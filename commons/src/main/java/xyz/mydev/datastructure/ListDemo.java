package xyz.mydev.datastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 范围定位
 *
 * @author ZSP
 */
public class ListDemo {
  public static void main(String[] args) {
    List<Rule> list = new ArrayList<>(10);
    list.add(new Rule(100, 10));
    list.add(new Rule(200, 20));
    list.add(new Rule(300, 30));
    list.add(new Rule(400, 40));
    list.add(new Rule(500, 50));

    int toAdd = getToAdd(330, list);
    System.out.println(toAdd);
    System.out.println("stream");


  }


  private static int getToAdd(int consumedBuy, List<Rule> ruleList) {
    if (CollectionUtils.isEmpty(ruleList)) {
      return 0;
    }

    return search(consumedBuy, ruleList);
  }

  private static int search(int consumedBuy, List<Rule> ruleList) {
    if (CollectionUtils.isEmpty(ruleList)) {
      return 0;
    }

    int size = ruleList.size();

    Rule min = ruleList.get(0);
    Rule max = ruleList.get(size - 1);

    // 区间外 consumedBuy not in  ( min,max]
    if (min.getQuota() > consumedBuy) {
      return 0;
    }
    if (max.getQuota() <= consumedBuy) {
      return max.getToAdd();
    }
    // 区间内 [min,max]
    Rule left = min;
    Rule right = max;

    boolean rightFlag = true;
    for (int i = 0; i < ruleList.size(); i++) {

      Rule rule = ruleList.get(i);
      int quota = rule.getQuota();

      if (quota == consumedBuy) {
        left = rule;
        right = rule;
        break;
      }

      // 如果大于，记录 right
      if (quota > consumedBuy && rightFlag) {
        right = rule;
        rightFlag = false;
      }
      // 如果小于，记录 left
      if (quota < consumedBuy) {
        left = rule;
      }
    }

    System.out.println(left);
    System.out.println(right);
    return left.getToAdd();
  }

  private static int searchByOrder(int consumedBuy, List<Rule> ruleList) {
    if (CollectionUtils.isEmpty(ruleList)) {
      return 0;
    }
    Rule result = null;
    for (int i = 0; i < ruleList.size(); i++) {
      Rule rule = ruleList.get(i);
      if (rule.getQuota() > consumedBuy) {
        if (i == 0) {
          return 0;
        }

        System.out.println(rule);
        result = rule;
        break;
      }
    }
    return result.getToAdd();
  }


  @Data
  @AllArgsConstructor
  private static class Rule {
    private Integer quota;
    private Integer toAdd;
  }
}
