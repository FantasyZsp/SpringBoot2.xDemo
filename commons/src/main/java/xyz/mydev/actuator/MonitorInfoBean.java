package xyz.mydev.actuator;

/**
 * @author zhao
 * @date 2018/09/13 10:39
 * @description
 */
public class MonitorInfoBean {
  /**
   * 可使用内存.
   */
  private long totalMemory;
  /**
   * 剩余内存.
   */
  private long freeMemory;
  /**
   * 最大可使用内存.
   */
  private long maxMemory;
  /**
   * 操作系统.
   */
  private String osName;
  /**
   * 总的物理内存.
   */
  private long totalPhysicalMemorySize;
  /**
   * 剩余的物理内存.
   */
  private long freePhysicalMemorySize;
  /**
   * 已使用的物理内存.
   */
  private long usedMemory;
  /**
   * 线程总数.
   */
  private int totalThread;
  /**
   * cpu使用率.
   */
  private double cpuRatio;


  public long getTotalMemory() {
    return totalMemory;
  }

  public void setTotalMemory(long totalMemory) {
    this.totalMemory = totalMemory;
  }

  public long getFreeMemory() {
    return freeMemory;
  }

  public void setFreeMemory(long freeMemory) {
    this.freeMemory = freeMemory;
  }

  public long getMaxMemory() {
    return maxMemory;
  }

  public void setMaxMemory(long maxMemory) {
    this.maxMemory = maxMemory;
  }

  public String getOsName() {
    return osName;
  }

  public void setOsName(String osName) {
    this.osName = osName;
  }

  public long getTotalPhysicalMemorySize() {
    return totalPhysicalMemorySize;
  }

  public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
    this.totalPhysicalMemorySize = totalPhysicalMemorySize;
  }

  public long getFreePhysicalMemorySize() {
    return freePhysicalMemorySize;
  }

  public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
    this.freePhysicalMemorySize = freePhysicalMemorySize;
  }

  public long getUsedMemory() {
    return usedMemory;
  }

  public void setUsedMemory(long usedMemory) {
    this.usedMemory = usedMemory;
  }

  public int getTotalThread() {
    return totalThread;
  }

  public void setTotalThread(int totalThread) {
    this.totalThread = totalThread;
  }

  public double getCpuRatio() {
    return cpuRatio;
  }

  public void setCpuRatio(double cpuRatio) {
    this.cpuRatio = cpuRatio;
  }
}

