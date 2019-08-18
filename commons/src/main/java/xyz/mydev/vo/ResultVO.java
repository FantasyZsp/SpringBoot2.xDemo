package xyz.mydev.vo;

/**
 * @author ZSP
 * @description 和现有ApiUtils的map结构一致
 * TODO 后续统一返回结构！！！！！！
 */
public class ResultVO<T> {
  private int statusCode;
  private String message;
  private T data;

  public ResultVO() {
  }

  public ResultVO(int statusCode, String message, T data) {
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
