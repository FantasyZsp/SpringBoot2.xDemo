package xyz.mydev.practice.socketio.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 配置
 *
 * @author ZSP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginInfo implements ClientInfoKey {
  @NotBlank
  private String roomKey;
  @NotBlank
  private String clientId;
  @NotBlank
  private String clientName;
  @NotBlank
  private String businessId;
  @NotBlank
  private String businessData;


}
