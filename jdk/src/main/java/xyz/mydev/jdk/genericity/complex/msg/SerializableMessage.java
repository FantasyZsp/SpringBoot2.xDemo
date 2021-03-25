package xyz.mydev.jdk.genericity.complex.msg;

import java.io.Serializable;

/**
 * @author ZSP
 */
public interface SerializableMessage<T extends Serializable> extends BaseMessage<T> {
}
