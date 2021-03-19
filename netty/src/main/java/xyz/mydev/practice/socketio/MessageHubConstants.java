package xyz.mydev.practice.socketio;

/**
 * @author ZSP  2019/03/27 16:23
 */
public interface MessageHubConstants {
  String ROOM_KEY = "roomKey";

  interface EventName {
    String EVENT_JOIN_ROOM = "JOIN_ROOM";
    String EVENT_LEAVE_ROOM = "LEAVE_ROOM";
    String EVENT_ONE = "EVENT_ONE";
  }


}
