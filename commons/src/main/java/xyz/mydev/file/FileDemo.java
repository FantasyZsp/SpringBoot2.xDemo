package xyz.mydev.file;

import java.io.File;

public class FileDemo {
  public static void main(String[] args) {
    listFileDirectory(new File("D:/workspace"));
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

