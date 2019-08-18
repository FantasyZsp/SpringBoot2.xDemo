package xyz.mydev.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author zhao
 * @date 2018/07/27 21:03
 * @description
 */
@Configuration
public class Swagger2Config {
  @Bean
  public Docket createRestApiDoc() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("imoocDemo")
      .apiInfo(apiInfo1())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.imooc"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo1() {
    return new ApiInfoBuilder()
      .title("Spring Boot中使用Swagger2构建RESTful APIs")
      .description("更多Spring Boot相关文章请关注：http://www.mydev.xyz/")
      .termsOfServiceUrl("http://www.mydev.xyz/")
//                .contact("FantasyZsp")
      .version("1.0")
      .build();
  }

  @Bean
  public Docket createRestApiDoc2() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("mapstruct")
      .apiInfo(apiInfo2())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.mapstruct"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo2() {
    return new ApiInfoBuilder()
      .title("Spring Boot中使用Swagger2构建RESTful APIs")
      .description("更多Spring Boot相关文章请关注：http://www.mydev.xyz/")
      .termsOfServiceUrl("http://www.mydev.xyz/")
//                .contact("FantasyZsp")
      .version("1.0")
      .build();
  }

  @Bean
  public Docket createRestApiDoc3() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("default")
      .apiInfo(apiInfo3())
      .select()
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo3() {
    return new ApiInfoBuilder()
      .title("Spring Boot中使用Swagger2构建RESTful APIs")
      .description("更多Spring Boot相关文章请关注：http://www.mydev.xyz/")
      .termsOfServiceUrl("http://www.mydev.xyz/")
//                .contact("FantasyZsp")
      .version("1.0")
      .build();
  }

  @Bean
  public Docket createRestApiDoc4() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("A jackson")
      .apiInfo(apiInfo4())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.jackson"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo4() {
    return new ApiInfoBuilder()
      .title("Spring Boot中jackson框架的使用")
      .description("更多Spring Boot相关文章请关注：http://www.mydev.xyz/")
      .termsOfServiceUrl("http://www.mydev.xyz/")
      .version("1.0")
      .build();
  }


  // 多包扫描
  private static final String splitor = ",";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("A FileUp")
      .apiInfo(apiInfo())
      .select()
      .apis(basePackage("xyz.mydev.jackson,xyz.mydev.filedemo"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("测试 APIs")
      .description("测试api接口文档")
      .version("1.0")
      .build();
  }


  public static Predicate<RequestHandler> basePackage(final String basePackage) {
    return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
  }

  private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
    return input -> {
      // 循环判断匹配
      for (String strPackage : basePackage.split(splitor)) {
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
