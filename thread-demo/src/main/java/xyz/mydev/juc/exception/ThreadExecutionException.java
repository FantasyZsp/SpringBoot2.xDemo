package xyz.mydev.juc.exception;

/**
 * @author ZSP
 */
public class ThreadExecutionException extends RuntimeException {
  public ThreadExecutionException() {
  }

  public ThreadExecutionException(String message) {
    super(message);
  }

  public ThreadExecutionException(Throwable cause) {
    super(cause);
  }
}
