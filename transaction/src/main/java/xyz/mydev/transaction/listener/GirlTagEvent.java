package xyz.mydev.transaction.listener;

import lombok.Getter;
import lombok.Setter;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author zhaosp
 */
@Getter
@Setter
public class GirlTagEvent extends GirlEvent {
  public GirlTagEvent(Girl girl) {
    super(girl);
  }
}