package xyz.mydev.vo;

import lombok.ToString;

/**
 * @author ZSP
 */
@ToString
public class ResultVO<T> {
  private Integer statusCode;
  private String message;
  private T data;

  private ResultVO() {
  }

  private ResultVO(int statusCode, String message, T data) {
    this.statusCode = statusCode;
    this.message = message;
    this.data = data;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static <T> ResultVO<T> success(T data) {
    return success(200, "success", data);
  }

  public static <T> ResultVO<T> success(int statusCode, String message) {
    return success(statusCode, message, null);
  }

  public static <T> ResultVO<T> success(int statusCode, String message, T data) {
    ResultVO<T> vo = new ResultVO<>();
    vo.setStatusCode(statusCode);
    vo.setMessage(message);
    vo.setData(data);
    return vo;
  }

  public static <T> ResultVO<T> failure(T data) {
    return failure(101, "failure", data);
  }

  public static <T> ResultVO<T> failure(int statusCode, String message) {
    return failure(statusCode, message, null);
  }

  public static <T> ResultVO<T> failure(int statusCode, String message, T data) {
    return success(statusCode, message, data);
  }


}
