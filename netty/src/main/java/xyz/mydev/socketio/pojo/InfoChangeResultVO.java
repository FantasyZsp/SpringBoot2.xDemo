package xyz.mydev.socketio.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ZSP  2019/03/30 18:45
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoChangeResultVO implements Serializable {
  @JsonProperty("success")
  private Boolean success;
  private String message;
}
