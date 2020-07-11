package xyz.mydev.jdk.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * [ERROR] /D:/projects/SpringBoot2.xDemo/jdk/src/main/java/xyz/mydev/jdk/inherit/SimpleEntity.java:[11,18] xyz.mydev.jdk.inherit.SimpleEntity中的getFlag()无法实现xyz.mydev.jdk.inherit.SuperEntity中的getFlag()
 * [ERROR]   返回类型java.lang.String与java.lang.Boolean不兼容
 *
 * @author ZSP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdTimeEntity implements Serializable {
  private String id;
  private LocalDateTime startTime;

}
