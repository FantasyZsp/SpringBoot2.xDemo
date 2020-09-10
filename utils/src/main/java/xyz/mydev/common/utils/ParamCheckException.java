package xyz.mydev.common.utils;

import javax.validation.ValidationException;
import java.util.Map;

/**
 * @author ZSP  2018/12/19 18:39
 */
public class ParamCheckException extends ValidationException {

  private Map<String, String> errors;

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }

  public ParamCheckException(Map<String, String> errors) {
    super("Param Check Error");
    this.errors = errors;
  }

  public ParamCheckException(String message) {
    super(message);
  }

  public ParamCheckException(String message, Map<String, String> errors) {
    super(message);
    this.errors = errors;
  }

  public ParamCheckException(Throwable cause) {
    super(cause);
  }

  public ParamCheckException(String message, Throwable cause) {
    super(message, cause);
  }
}