package xyz.mydev.jdk.genericity.complex.port;

/**
 * @author ZSP
 */
public interface Porter<E> {

  default void transfer(E e) {
  }

  default void port(E e) {
  }
}
