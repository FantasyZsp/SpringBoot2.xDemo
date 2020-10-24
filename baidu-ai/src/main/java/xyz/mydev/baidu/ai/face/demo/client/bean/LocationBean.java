package xyz.mydev.baidu.ai.face.demo.client.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhaosp
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LocationBean {
  private Double left;
  private Double top;
  private Integer width;
  private Integer height;
  private Integer rotation;
}