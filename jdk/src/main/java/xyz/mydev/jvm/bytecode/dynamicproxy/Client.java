package xyz.mydev.jvm.bytecode.dynamicproxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author ZSP
 */
public class Client {
  public static void main(String[] args) {


    // 将动态代理生成的代理类写到磁盘上。默认写到项目根目录 com/sun/proxy下
    // 已copy到本目录 $Proxy0.txt 中
    System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

    RealSubject realSubject = new RealSubject();

    DynamicSubject dynamicSubject = new DynamicSubject(realSubject);
    Object proxyInstance = Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), dynamicSubject);

    System.out.println();
    System.out.println(proxyInstance.getClass());
    System.out.println(proxyInstance.getClass().getSuperclass());

    System.out.println(Arrays.toString(proxyInstance.getClass().getInterfaces()));
    Subject subject = (Subject) proxyInstance;

    subject.request();

    subject.valid();

    Marker marker = (Marker) proxyInstance;

    marker.mark();


  }
}
