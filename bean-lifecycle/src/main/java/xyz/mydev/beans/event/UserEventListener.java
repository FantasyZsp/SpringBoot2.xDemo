package xyz.mydev.beans.event;

import org.springframework.context.ApplicationListener;
import xyz.mydev.beans.pojo.User;

/**
 * @author ZSP
 */
public class UserEventListener implements ApplicationListener<GenericEvent<User>> {
  @Override
  public void onApplicationEvent(GenericEvent<User> event) {
    System.out.println(event);
    System.out.println(event.getSource());
  }
}
