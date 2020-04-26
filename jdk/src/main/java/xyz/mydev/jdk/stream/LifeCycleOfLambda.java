package xyz.mydev.jdk.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZSP
 */
public class LifeCycleOfLambda {
  public static void main(String[] args) {
    final int max = 10;

    List<Invoker<Integer>> list = new ArrayList<>(max);
    List<Invoker<AtomicInteger>> list2 = new ArrayList<>(max);
    List<InvokerWrapper> wrapperList = new ArrayList<>(max);
    for (int i = 0; i < max; i++) {
      list.add(new Invoker<>(i));
    }

    for (AtomicInteger i = new AtomicInteger(0); i.get() < max; i.incrementAndGet()) {
      list2.add(new Invoker<>(i));
      wrapperList.add(new InvokerWrapper(i));
    }

    System.out.println("list");
    list.forEach(Invoker::invoke);
    System.out.println("list2");
    list2.forEach(Invoker::invoke);
    System.out.println("wrapperList");
    wrapperList.forEach(InvokerWrapper::invoke);


//    for (int i = 0; i < max; i++) {
//      final int temp = i;
//      Runnable runnable = () -> System.out.println(temp);
//    }
  }
}

class Invoker<T> {

  private T number;

  public Invoker(T number) {
    this.number = number;
  }

  public void invoke() {
    System.out.println(number);
  }
}

class InvokerWrapper {

  private AtomicInteger number;

  public InvokerWrapper(AtomicInteger number) {
    this.number = number;
  }

  public void invoke() {
    System.out.println(number);
  }
}
