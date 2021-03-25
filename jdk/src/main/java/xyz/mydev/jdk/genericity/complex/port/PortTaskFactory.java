package xyz.mydev.jdk.genericity.complex.port;

/**
 * @author ZSP
 */
@FunctionalInterface
public interface PortTaskFactory<E> extends TaskFactory<E> {

  default Runnable newPortTask(E e) {
    return newTask(e);
  }

}
