package xyz.mydev.beans;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhaosp
 */
public class LocalApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(Object.class).listeners(event -> {

      System.out.print(event.getClass().getSimpleName() + ":  ");
      System.out.println(event.getSource().getClass());
    })
      .web(WebApplicationType.NONE)
      .run(args)
      .close();

  }
}
