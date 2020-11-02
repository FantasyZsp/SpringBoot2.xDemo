package xyz.mydev.proxy.demo.jdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZSP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestParam {
  private String content;
}
