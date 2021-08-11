package xyz.mydev.test;

/**
 * @author ZSP
 */
public class ReplaceChars {
  public static void main(String[] args) {
    System.out.println(replace("abc", "bc", "d"));
    System.out.println(replace("abcdefgggg", "g", "d"));
    System.out.println(replace("aaaa", "b", "ccccc"));
  }

  public static String replace(String a, String b, String c) {
    // check
    if (a == null || a.isEmpty()) {
      return a;
    }

    if (b == null || b.isEmpty()) {
      return a;
    }

    // c如果是空串，替换也就代表删除，这里就不考虑了。
    if (c == null || c.isEmpty()) {
      return a;
    }

    if (a.length() < b.length()) {
      return a;
    }

    char[] charsA = a.toCharArray();
    char[] charsB = b.toCharArray();
    char[] charsC = c.toCharArray();

    StringBuilder result = new StringBuilder();

    for (int indexFromA = 0; indexFromA < charsA.length; ) {
      boolean isMatched = match(charsA, indexFromA, charsB);
      if (isMatched) {
        result.append(charsC);
        indexFromA += charsB.length;
      } else {
        // 没有匹配，就追加当前字符，并下移指针indexFromA
        result.append(charsA[indexFromA]);
        indexFromA++;
      }

    }
    return result.toString();


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

}
