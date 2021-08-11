package xyz.mydev.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */
public class ReplaceChars {
  public static void main(String[] args) {
    System.out.println(replace("abc", "bc", "d"));
    System.out.println(replace("abcdefgggg", "g", "d"));
    System.out.println(replace("aaaa", "b", "ccccc"));
    // error case
    System.out.println(replace("aaaa", "a", "aa"));
  }

  public static String replace(String a, String b, String c) {
    ReplaceResult replaceResult = new ReplaceResult(false, a, false);
    do {
      replaceResult = replaceInner(replaceResult.result, b, c);
    } while (!replaceResult.completed);
    return replaceResult.result;
  }


  public static ReplaceResult replaceInner(String a, String b, String c) {
    // check
    if (a == null || a.isEmpty()) {
      return new ReplaceResult(false, a, true);
    }

    if (b == null || b.isEmpty()) {
      return new ReplaceResult(false, a, true);
    }

    // c如果是空串，替换也就代表删除，这里就不考虑了。
    if (c == null || c.isEmpty()) {
      return new ReplaceResult(false, a, true);
    }

    if (a.length() < b.length()) {
      return new ReplaceResult(false, a, true);
    }

    // 导致替换异常的几个case检测，注：这里只是把第二问的情况列出来，所以会用到String内置方法。
    // 但是并不影响替换算法中使用的api，个人认为并不犯规。

    // ex case1: c中本来就包含b，导致第一轮发生了替换后，后续必然还会有b的存在，导致无限替换下去。
    // 循环算法会导致OOM，递归算法可能SOF也可能OOM
    if (c.contains(b)) {
      throw new IllegalArgumentException("c contains b ");
    }


    char[] charsA = a.toCharArray();
    char[] charsB = b.toCharArray();
    char[] charsC = c.toCharArray();

    StringBuilder result = new StringBuilder();

    boolean matched = false;
    for (int indexFromA = 0; indexFromA < charsA.length; ) {
      boolean isMatchedThisTime = match(charsA, indexFromA, charsB);
      if (isMatchedThisTime) {
        matched = true;
        result.append(charsC);
        indexFromA += charsB.length;
      } else {
        // 没有匹配，就追加当前字符，并下移指针indexFromA
        result.append(charsA[indexFromA]);
        indexFromA++;
      }
    }
    // 如果发生过匹配替换，a可能还会有b存在；如果没发生过，继续下轮匹配，直到匹配不上
    return new ReplaceResult(matched, result.toString(), !matched);
  }

  private static boolean match(char[] charsA, int indexFromA, char[] charsB) {
    if (indexFromA > charsA.length - 1) {
      return false;
    }

    // 没有足够的长度去匹配B
    if (charsA.length - indexFromA < charsB.length) {
      return false;
    }

    for (int i = 0; i < charsB.length; i++, indexFromA++) {
      // 依次匹配对位字符
      if (charsA[indexFromA] != charsB[i]) {
        return false;
      }
    }
    return true;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @ToString
  public static class ReplaceResult {
    // 是否有匹配命中
    private boolean matched;
    // 本轮结果
    private String result;
    // 是否替换完毕
    private boolean completed;
  }

}
