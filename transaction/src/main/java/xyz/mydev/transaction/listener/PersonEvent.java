package xyz.mydev.transaction.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import xyz.mydev.common.beans.Person;

/**
 * @author zhaosp
 */
@Getter
@Setter
public class PersonEvent extends ApplicationEvent {

  private Object data;

  public PersonEvent(Person person) {
    super(person);
    this.data = person;
  }
}