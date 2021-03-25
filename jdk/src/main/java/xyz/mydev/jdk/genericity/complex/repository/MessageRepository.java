package xyz.mydev.jdk.genericity.complex.repository;

import xyz.mydev.jdk.genericity.complex.msg.StringMessage;

import java.time.LocalDateTime;

public interface MessageRepository<T extends StringMessage> extends MessageCrudRepository<T, String, LocalDateTime> {

  String getTableName();

}