package xyz.mydev.orm.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://baomidou.com/guide/quick-start.html#%E9%85%8D%E7%BD%AE
 *
 * @author zhaosp
 */
@SpringBootApplication
@MapperScan("xyz.mydev.orm.mybatisplus.example.mapper")
public class ExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }

}