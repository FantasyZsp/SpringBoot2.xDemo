package xyz.mydev.actuator;

/**
 * 获取系统信息的业务逻辑类接口.
 */
public interface IMonitorService {
  /**
   * 获得当前的监控对象.
   *
   * @return 返回构造好的监控对象
   * @throws Exception 异常
   */
  public MonitorInfoBean getMonitorInfoBean() throws Exception;
}

