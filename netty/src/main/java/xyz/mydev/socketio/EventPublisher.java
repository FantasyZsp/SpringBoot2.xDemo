package xyz.mydev.socketio;

/**
 * @author ZSP  2019/03/27 16:29
 */
public interface EventPublisher {
  void broadcastInRoom(String roomName, String eventName, Object message);
}
