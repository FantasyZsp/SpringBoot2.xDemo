package xyz.mydev.jdk.genericity.complex.port;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.mydev.jdk.genericity.complex.msg.SerializableMessage;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

/**
 * 消息搬运工
 * 处理缓存、去重、可靠中转等逻辑
 *
 * @author ZSP
 */
@Slf4j
@Getter
@Setter
public class DefaultPorterV2<E extends SerializableMessage<? extends Serializable>> extends AbstractPorter<E> {

  private ExecutorService portExecutor;
  private ExecutorService transferExecutor;

  private final TransferTaskFactory<E> transferTaskFactory;
  private final PortTaskFactory<E> portTaskFactory;

  public DefaultPorterV2(TransferTaskFactory<E> transferTaskFactory,
                         PortTaskFactory<E> portTaskFactory) {
    this.transferTaskFactory = transferTaskFactory;
    this.portTaskFactory = portTaskFactory;
  }


}