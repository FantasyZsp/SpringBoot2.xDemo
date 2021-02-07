package xyz.mydev.spring.aop;

import org.springframework.aop.framework.ProxyFactory;

/**
 * {@link ProxyFactory} 根据别代理对象是否实现了接口去选择代理方式
 *
 * @author ZSP
 */
public class ProxyFactoryDemo {

  public static void main(String[] args) {

    System.out.println("代理孤类");
    NoIfEchoService echoService = new NoIfEchoService();
    ProxyFactory proxyFactory4SingleClass = new ProxyFactory(echoService);
    proxyFactory4SingleClass.addAdvice(new CommonMethodInterceptor());

    NoIfEchoService proxy = (NoIfEchoService) proxyFactory4SingleClass.getProxy();
    System.out.println(proxy.echo("test 代理孤类"));
    System.out.println(proxy.toString());
    System.out.println(proxy.getClass());


    System.out.println("代理接口实现类");
    EchoService echoService2 = new DefaultEchoService();
    ProxyFactory proxyFactory4ImplOne = new ProxyFactory(echoService2);
    proxyFactory4ImplOne.addAdvice(new CommonMethodInterceptor());

    EchoService proxy2 = (EchoService) proxyFactory4ImplOne.getProxy();
    System.out.println(proxy2.echo("test 代理接口实现类"));
    System.out.println(proxy2.toString());
    System.out.println(proxy2.getClass());


  }

}
