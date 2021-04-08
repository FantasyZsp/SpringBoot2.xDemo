package xyz.mydev.orm.mybatisplus.example.domain;

import lombok.Data;

/**
 * @author zhaosp
 */
@Data
public class User {
  private Long id;
  private String name;
  private Integer age;
  private String email;
}