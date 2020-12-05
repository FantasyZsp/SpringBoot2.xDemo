package xyz.mydev;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
public class ComponentNeedImport {

  @Bean
  public String componentNeedImportInnerBeanMethod() {
    return "componentNeedImportInnerBeanMethod";
  }

}
