package xyz.mydev.spring.aop;

/**
 * @author ZSP
 */
public class DefaultEchoService implements EchoService {
  @Override
  public String echo(String content) {
    return "ECHO: " + content;
  }
}
