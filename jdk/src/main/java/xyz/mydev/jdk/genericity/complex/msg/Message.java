package xyz.mydev.jdk.genericity.complex.msg;


/**
 * @author ZSP
 */
public interface Message extends StringMessage {

  String getTraceId();

  String getTraceVersion();

}
