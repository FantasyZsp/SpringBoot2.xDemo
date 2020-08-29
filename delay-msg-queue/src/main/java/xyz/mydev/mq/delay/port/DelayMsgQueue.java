package xyz.mydev.mq.delay.port;

import java.util.Queue;

/**
 * 增删查、去重
 *
 * @author ZSP
 */
public interface DelayMsgQueue<E> {


  boolean put(E e);

  E take() throws InterruptedException;

  boolean contains(E e);

  boolean remove(E e);

  Queue<E> getTargetQueue();

  void start();

  void destroy();

}
