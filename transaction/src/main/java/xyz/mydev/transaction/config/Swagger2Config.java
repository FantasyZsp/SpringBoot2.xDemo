package xyz.mydev.transaction.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author ZSP
 */
@Configuration
@EnableSwagger2
@SuppressWarnings("all")
public class Swagger2Config {
  private final String basePackage = "xyz.mydev";

  @Bean
  public Docket createRestApiDoc() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("transaction demo")
      .apiInfo(apiInfo())
      .select()
      .apis(basePackage(basePackage))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("测试事务特性")
      .version("1.0")
      .build();
  }


  /**
   * 多包扫描分隔符
   */
  private static final String SEPARATOR = ",";


  private static Predicate<RequestHandler> basePackage(final String basePackage) {
    return input -> {
      assert input != null;
      return declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    };
  }

  private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
    return input -> {
      // 循环判断匹配
      for (String strPackage : basePackage.split(SEPARATOR)) {
        boolean isMatch = input.getPackage().getName().startsWith(strPackage);
        if (isMatch) {
          return true;
        }
      }
      return false;
    };
  }

  private static Optional<Class<?>> declaringClass(RequestHandler input) {
    return Optional.ofNullable(input.declaringClass());
  }
}
