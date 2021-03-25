package xyz.mydev.jdk.genericity.complex;

import xyz.mydev.jdk.genericity.complex.msg.StringMessage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZSP
 */
public interface MessageLoader<T extends StringMessage> {

  List<T> load(String targetTableName, LocalDateTime startTime, LocalDateTime endTime);


}
