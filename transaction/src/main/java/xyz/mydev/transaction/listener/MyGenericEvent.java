package xyz.mydev.transaction.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * @author ZSP
 */
public class MyGenericEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
  public MyGenericEvent(T source) {
    super(source);
  }

  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
  }
}
