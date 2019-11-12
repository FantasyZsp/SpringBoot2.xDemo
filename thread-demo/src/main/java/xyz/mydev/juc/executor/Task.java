package xyz.mydev.juc.executor;

/**
 * @author ZSP  2019/09/20 17:58
 */
public class Task implements Runnable {
  private Result result;
  private Object resultData;

  public Task(Result result, Object resultData) {
    this.result = result;
    this.resultData = resultData;
  }

  @Override
  public void run() {
    result.setData(resultData);
    result.setCode(2);
    result.setMsg("Task Msg");
  }
}