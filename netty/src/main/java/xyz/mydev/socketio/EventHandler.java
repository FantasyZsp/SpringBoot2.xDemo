package xyz.mydev.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.socketio.pojo.ChatObject;

import static xyz.mydev.socketio.MessageHubConstants.ROOM_KEY;

/**
 * 定义事件处理
 * 1、连接事件。当有客户端连接时，将其加入到对应携带的门机code的房间中。
 * 2、断开连接时间。离开房间。
 * 3、广播事件:
 * * 1、
 *
 * @author zhao
 */
//@Component
@Slf4j
public class EventHandler {
  private SocketIOServer socketIoServer;
  private ObjectMapper objectMapper;

  @Autowired
  public EventHandler(SocketIOServer server,
                      ObjectMapper objectMapper) {
    this.socketIoServer = server;
    this.objectMapper = objectMapper;
  }

  @OnConnect
  public void onConnect(SocketIOClient client) throws JsonProcessingException {
    printClientInfo(client);
    String roomName = client.getHandshakeData().getSingleUrlParam(ROOM_KEY);
    client.set(ROOM_KEY, client.getHandshakeData().getSingleUrlParam(ROOM_KEY));
    client.joinRoom(roomName);
  }


  @OnDisconnect
  public void onDisconnect(SocketIOClient client) {
    client.leaveRoom(client.get(ROOM_KEY));
    log.info("=====> client: {}  disconnected", client.getSessionId());
  }

  @OnEvent("message")
  public void onEvent(SocketIOClient client, ChatObject data) throws JsonProcessingException {
    log.info("onConnect ==> 客户端ID: {}", client.getSessionId());
    log.info("onConnect ==> 客户端getAllRooms: {}", client.getAllRooms());
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
    String roomName = client.get(ROOM_KEY);
    ChatObject chatObject = new ChatObject();
    chatObject.setUserName(data.getUserName());
    chatObject.setMessage(String.format("welcome %s to room %s", chatObject.getUserName(), roomName));
    socketIoServer.getRoomOperations(client.get(ROOM_KEY)).sendEvent("message", chatObject);
  }


  public void pushMessage(String receiver, String eventName, String message) {
  }


  private void printClientInfo(SocketIOClient client) {
    log.info("onConnect ==> 客户端ID: {}", client.getSessionId());
    log.info("onConnect ==> 客户端craneCode: {}", client.getHandshakeData().getSingleUrlParam(ROOM_KEY));
    log.info("onConnect ==> 客户端getTransport: {}", client.getTransport());
    log.info("onConnect ==> 客户端getAllRooms: {}", client.getAllRooms());
    log.info("onConnect ==> 客户端getRemoteAddress: {}", client.getRemoteAddress());
  }
}
