package xyz.mydev.jdk.genericity.complex.route;

import xyz.mydev.jdk.genericity.complex.msg.StringMessage;
import xyz.mydev.jdk.genericity.complex.repository.MessageRepository;
import xyz.mydev.jdk.genericity.complex.route.Router;

public interface MessageRepositoryRouter<T extends StringMessage> extends Router<String, MessageRepository<T>> {
  @Override
  MessageRepository<T> get(String msgTableName);


  default MessageRepository<T> resolveByMessage(T msg) {
    return get(msg.getTargetTableName());
  }
}