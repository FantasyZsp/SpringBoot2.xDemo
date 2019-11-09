package xyz.mydev.filedemo;


import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author zhao
 * @date 2018/07/31 20:45
 * @description
 */
public class FileDemo {
  public static void main(String[] args) {

  }

  private String getJson() {
    // String path = getClass().getClassLoader().getResource("init.json").toString();
    String path = getClass().getClassLoader().getResource("src/main/resources/init.json").getPath();
    path = path.replace("\\", "/");
    if (path.contains(":")) {
      path = path.replace("file:/", "");
    }
    try {
      String input = FileUtils.readFileToString(new File(path), "UTF-8");
      return input;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static void listFileDirectory(File file) {
    if (!file.exists()) {
      System.out.println("目录不存在！");
      return;

    } else if (!file.isDirectory()) {
      System.out.println("这是个文件。路径是：" + file);
      return;
    } else {
      File[] files = file.listFiles();
      if (files != null && file.length() > 0) {
        for (File f : files) {
          if (f.isDirectory()) {
            listFileDirectory(f);
          } else {
            System.out.println(f);
          }
        }
      }
    }
  }
}
