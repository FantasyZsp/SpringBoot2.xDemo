package xyz.mydev.baidu.ai.face.exception;

/**
 * 可重试异常
 *
 * @author ZSP
 */
public class RetryableException extends RuntimeException {

  public RetryableException() {
  }

  public RetryableException(String message) {
    super(message);
  }
}
