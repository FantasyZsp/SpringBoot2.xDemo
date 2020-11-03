package xyz.mydev.baidu.ai.face.exception;

/**
 * 内部限流异常
 *
 * @author ZSP
 */
public class RateLimitException extends RuntimeException {

  public RateLimitException() {
  }

  public RateLimitException(String message) {
    super(message);
  }
}
