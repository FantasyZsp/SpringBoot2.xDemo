package xyz.mydev.jvm.classload.init;

/**
 * @author ZSP
 */
public class RefInCycle {
  public static void main(String[] args) {
    System.out.println(Const.SERVER_ERR);
  }
}


class Const {

  static {
    System.out.println("Const static invoke");
  }


  static {
    System.out.println("Const static invoke after SUCCESS_ENTITY");
  }

  public static final Integer SUCCESS = 200;
  public static final Integer BAD_REQUEST = 400;
  public static final Integer SERVER_ERR = 500;

  public static final ResponseResult SUCCESS_ENTITY = new ResponseResult(Const.SUCCESS, null);


}

class ResponseResult {

  private Integer code;

  private String message;

  private Object data;

  public ResponseResult() {
    System.out.println("ResponseResult constructor1 invoke");
  }

  static {
    System.out.println("ResponseResult static invoke");
  }

  {
    System.out.println("ResponseResult object invoke");
  }

  public ResponseResult(Integer code, String message) {
    System.out.println("ResponseResult constructor2 invoke");
    this.code = code;
    this.message = message;
  }

  public ResponseResult(Integer code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}