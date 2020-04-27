package xyz.mydev.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 设定虚拟机参数  -Xms5m -Xmx5m以更好地重现内存溢出错误
 * 添加虚拟机参数 -XX:+HeapDumpOnOutOfMemoryError 将异常信息写入文件中，文件位于项目根目录。
 *
 * @author ZSP
 */
public class OutOfMemoryErrorTest {
  public static void main(String[] args) {
    List<Object> list = new ArrayList<>();
    while (true) {
      list.add(new OutOfMemoryErrorTest());
      System.gc();
    }
  }
}
