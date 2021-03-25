package xyz.mydev.jdk.genericity.complex.port;

import javax.validation.constraints.NotNull;

/**
 * @author ZSP
 */
@FunctionalInterface
public interface TaskFactory<E> {
  @NotNull
  Runnable newTask(E e);
}
