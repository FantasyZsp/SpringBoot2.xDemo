package xyz.mydev.practice.socketio.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZSP  2019/3/27 16:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientSimpleMessage implements Serializable {
  private String clientId;
  private String roomKey;
  private String message;
}
