package xyz.mydev.jvm.memory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import xyz.mydev.utils.ThreadUtils;

/**
 * 方法区产生OOM
 * 设定虚拟机参数，-XX:MaxMetaspaceSize=10m 控制元空间大小
 *
 * @author ZSP
 */
public class MethodAreaOOMTest {

  public static void main(String[] args) {
    int count = 0;
    ThreadUtils.sleepSeconds(5);
    try {
      for (; ; ) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MethodAreaOOMTest.class);
        enhancer.setUseCache(false);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args));
        enhancer.create();
        count++;
        ThreadUtils.join(300);
      }
    } catch (Throwable e) {
      System.out.println(count);
      e.printStackTrace();
    }
  }
}
