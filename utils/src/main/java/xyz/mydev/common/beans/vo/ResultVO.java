package xyz.mydev.common.beans.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZSP
 */
@ToString
@Getter
@Setter
public class ResultVO<T> {
  private Integer status;
  private String message;
  private T data;

  private ResultVO() {
  }

  private ResultVO(int status, String message, T data) {
    this.status = status;
    this.message = message;
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
    vo.setStatus(statusCode);
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

  public boolean success() {
    return this.status == 200;
  }


}
