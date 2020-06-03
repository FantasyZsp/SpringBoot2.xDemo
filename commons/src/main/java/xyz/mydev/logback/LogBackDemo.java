package xyz.mydev.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhao
 * @date 2018/07/31 20:11
 * @description 参考https://segmentfault.com/a/1190000008315137
 * @description 参考https://www.cnblogs.com/warking/p/5710303.html
 * @description 参考https://blog.csdn.net/evankaka/article/details/50637994
 */
public class LogBackDemo {
  //    日志级别
//    TRACE < DEBUG < INFO < WARN < ERROR
  // 单例模式获取bean。入参指定了bean的标识，用于指出是哪个类产生的。
  private static final Logger LOGGER = LoggerFactory.getLogger(LogBackDemo.class);

  public static void main(String[] args) {

    LOGGER.debug("========debug=========");
    LOGGER.info("========info=========");
    LOGGER.warn("========warn=========");
    LOGGER.error("========error=========");
//
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        // 输出异常栈时打印出jar包信息
//        lc.setPackagingDataEnabled(true);
//        StatusPrinter.print(lc);
//
//        Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        System.out.println(rootLogger);
//        System.out.println(1/0);
  }
}
