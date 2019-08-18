package xyz.mydev.filedemo;


import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author  zhao
 * @date  2018/07/31 20:45
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
}
