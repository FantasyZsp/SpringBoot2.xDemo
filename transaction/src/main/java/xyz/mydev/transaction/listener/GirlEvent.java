package xyz.mydev.transaction.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author zhaosp
 */
@Getter
@Setter
public class GirlEvent extends ApplicationEvent {

  private Object data;

  public GirlEvent(Girl girl) {
    super(girl);
    this.data = girl;
  }
}