package xyz.mydev.baidu.ai.face.client.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhaosp
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LocationBean {
  private Double left;
  private Double top;
  private Integer width;
  private Integer height;
  private Integer rotation;
}