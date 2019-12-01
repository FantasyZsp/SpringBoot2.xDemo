package xyz.mydev.jdk.random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author  zhao
 * @date  2018/09/04 15:25
 * @description
 */
public class MyRandom {
  public static void main(String[] args) throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();

    int number5 = (int) ((Math.random() * 9 + 1) * 10000);
    int number7 = (int) ((Math.random() * 9 + 1) * 1000000);
    int number8 = (int) ((Math.random() * 9 + 1) * 10000000);
    System.out.println(new Random().nextInt(10));
    System.out.println(new Random().nextInt(10));
    System.out.println(new Random().nextInt(10));
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 34个省份
    String provinces = "京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川黔滇藏陕甘青宁新台港澳";

    String iso = "20FR\n" +
      "20GP\n" +
      "20RF\n" +
      "20OT\n" +
      "20HW\n" +
      "20RH\n" +
      "20TK\n" +
      "20HC\n" +
      "20SH\n" +
      "20HT\n" +
      "20FR\n" +
      "20TK\n" +
      "22GW\n" +
      "22TN\n" +
      "22V1\n" +
      "22T2\n" +
      "25GW\n" +
      "25GP\n" +
      "40FR\n" +
      "40GP\n" +
      "40HT\n" +
      "40OT\n" +
      "40HQ\n" +
      "40GP\n" +
      "40RF\n" +
      "45GW\n" +
      "45HT\n" +
      "45P4\n" +
      "40RH\n" +
      "45S3\n" +
      "45U1\n" +
      "45HQ\n" +
      "45HW\n" +
      "45HC\n" +
      "45GP\n" +
      "45HC\n";
    String[] isoArray = StringUtils.split(iso, "\n");
    for (String str : isoArray) {
      System.out.print(str + " ");
    }
    System.out.println();

    String iso2 = iso.replaceAll("[\b\r\n\t]*", "");
    System.out.println("iso2 " + iso2);


    List<String> iso3 = new ArrayList<>();
    String temp = "";
    for (int i = 0; i < iso2.length(); i++) {
      temp = temp + "" + iso2.charAt(i);
      if ((i + 1) % 4 == 0) {
        iso3.add(temp);
        temp = "";
      }
    }
    System.out.println("iso3 " + objectMapper.writeValueAsString(iso3));
    System.out.println(iso3.get(1));
    System.out.println(iso3.get(1).length());

    String str111[] = {"20FR", "20GP", "20RF", "20OT", "20HW", "20RH", "20TK", "20HC", "20SH", "20HT", "20FR", "20TK", "22GW", "22TN", "22V1", "22T2", "25GW", "25GP", "40FR", "40GP", "40HT", "40OT", "40HQ", "40GP", "40RF", "45GW", "45HT", "45P4", "40RH", "45S3", "45U1", "45HQ", "45HW", "45HC", "45GP", "45HC"};
    System.out.println("======String[]=======");
    List list = Arrays.asList(str111);
    System.out.println(list);
    System.out.println(list.get(3));
    System.out.println(objectMapper.writeValueAsString(list));


    // 去除回车、空格、制表符
    // System.out.println(provinces.replaceAll("[\b\r\n\t]*", ""));
    char az = chars.charAt((int) (Math.random() * 26));
    String azLists = "";
    for (int i = 0; i < 4; i++) {
      azLists = azLists + chars.charAt((int) (Math.random() * 26));
    }

    char province = provinces.charAt((int) (Math.random() * 34));
    String licensePlateNumber = "" + province + az + number5;

    String containerNumber = azLists + number7;


    System.out.println("number5 " + number5);
    System.out.println("number7 " + number7);
    System.out.println("number8 " + number8);
    System.out.println("一位大写字母 " + az);
    System.out.println("四位大写字母 " + azLists);
    System.out.println("箱号 " + containerNumber);
    System.out.println("省份 " + province);
    System.out.println("车牌  " + licensePlateNumber);

  }

//    public static String getRandomCharAndNumr(Integer length) {
//        String str = "";
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            boolean b = random.nextBoolean();
//            if (b) { // 字符串
//                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
//                str += (char) (65 + random.nextInt(26));// 取得大写字母
//            } else { // 数字
//                str += String.valueOf(random.nextInt(10));
//            }
//        }
//        return str;
//    }
}
