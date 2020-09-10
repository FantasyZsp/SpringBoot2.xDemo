package xyz.mydev.socketio.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.mydev.common.beans.vo.ResultVO;
import xyz.mydev.common.utils.ParamCheckException;
import xyz.mydev.socketio.EventPublisher;
import xyz.mydev.socketio.pojo.ClientSimpleMessage;
import xyz.mydev.socketio.pojo.InfoChangeResultVO;
import xyz.mydev.socketio.pojo.LoginInfo;

import static xyz.mydev.common.utils.BeanValidatorUtil.checkValid;
import static xyz.mydev.socketio.MessageHubConstants.EventName.EVENT_JOIN_ROOM;
import static xyz.mydev.socketio.MessageHubConstants.EventName.EVENT_LEAVE_ROOM;
import static xyz.mydev.socketio.MessageHubConstants.EventName.EVENT_ONE;
import static xyz.mydev.socketio.MessageHubConstants.ROOM_KEY;

/**
 * 定义事件处理
 * 1、连接事件。当有客户端连接时，将其加入到对应携带的code的房间中。
 * 2、断开连接时间。离开房间。
 * 3、广播事件:
 * * 1、在某房间发布某消息。
 *
 * @author zhao
 */
@Component
@Slf4j
public class MessageHub implements EventPublisher {
  private SocketIOServer socketIoServer;
  private ObjectMapper objectMapper;


  @Autowired
  public MessageHub(SocketIOServer server, ObjectMapper objectMapper) {
    this.socketIoServer = server;
    this.objectMapper = objectMapper;
  }

  @OnConnect
  public void onConnect(SocketIOClient client) {
    printClientInfo(client);
    HandshakeData data = client.getHandshakeData();
    String roomName = data.getSingleUrlParam(ROOM_KEY);
    LoginInfo loginInfo = buildLoginInfo(data);
    try {
      checkValid(loginInfo);
    } catch (ParamCheckException e) {
      log.error("连接参数不合法 {}", e.getMessage());
      client.disconnect();
      return;
    }
    ResultVO<LoginInfo> rpcResult;
    try {
      // rpc
      rpcResult = ResultVO.success(loginInfo);
    } catch (Exception e) {
      log.error("登录异常", e);
      client.disconnect();
      return;
    }

    if (rpcResult.success()) {
      client.set(ROOM_KEY, roomName);
      client.joinRoom(roomName);

      ClientSimpleMessage clientSimpleMessage = ClientSimpleMessage.builder()
        .roomKey(loginInfo.getRoomKey())
        .clientId(client.getSessionId().toString())
        .message(String.format("welcome %s[%s] to join room [%s]", client.getSessionId().toString(), rpcResult.getData().getClientName(), roomName))
        .build();

      log.info(clientSimpleMessage.getClientId() + " has connected");
      log.info(clientSimpleMessage.getMessage());
      client.sendEvent(EVENT_ONE, "HELLO, " + loginInfo.getClientName() + "!");
      broadcastInRoom(roomName, EVENT_JOIN_ROOM, clientSimpleMessage);
    } else {
      log.error("登录失败: {}", rpcResult);
      client.disconnect();
    }
  }

  public static LoginInfo buildLoginInfo(HandshakeData data) {
    return LoginInfo.builder()
      .roomKey(data.getSingleUrlParam(LoginInfo.ROOM_KEY))
      .clientId(data.getSingleUrlParam(LoginInfo.CLIENT_ID))
      .clientName(data.getSingleUrlParam(LoginInfo.CLIENT_NAME))
      .businessId(data.getSingleUrlParam(LoginInfo.BUSINESS_ID))
      .businessData(data.getSingleUrlParam(LoginInfo.BUSINESS_DATA))
      .build();
  }


  @OnDisconnect
  public void onDisconnect(SocketIOClient client) {
    String roomName = client.get(ROOM_KEY);
    // 当房间存在，退出房间
    if (StringUtils.isNotBlank(roomName)) {
      ClientSimpleMessage clientSimpleMessage = ClientSimpleMessage.builder()
        .roomKey(roomName)
        .clientId(client.getSessionId().toString())
        .message(String.format("%s leave room %s", client.getSessionId().toString(), roomName))
        .build();
      client.leaveRoom(roomName);
      log.info(clientSimpleMessage.getMessage());
      log.info(clientSimpleMessage.getClientId() + " has disconnected");
      broadcastInRoom(roomName, EVENT_LEAVE_ROOM, clientSimpleMessage);
      // 不存在，直接断开连接
    } else {
      client.disconnect();
      log.info(client.getSessionId() + " has disconnected");
    }
  }


  /**
   * 当配置更改时，维护配置。
   * 判断岸桥号是否变更。如果变更，需要切换到新的房间。
   */
  @OnEvent("CHANGE_INFO")
  public void onChangeInfo(SocketIOClient client, LoginInfo loginInfo, AckRequest ackRequest) throws JsonProcessingException {
    log.info("客户端:{}==>变更配置", client.getSessionId());
    log.info("配置信息: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginInfo));

    try {
      checkValid(loginInfo);
    } catch (ParamCheckException e) {
      log.error("连接参数不合法{}", loginInfo);
      ackRequest.sendAckData(InfoChangeResultVO.builder().success(false).message(e.getMessage()).build());
      return;
    }

    ResultVO<LoginInfo> configResult;
    try {
      // rpc
      configResult = ResultVO.success(loginInfo);
    } catch (Exception e) {
      log.error("配置异常", e);
      ackRequest.sendAckData(InfoChangeResultVO.builder().success(false).message(e.getMessage()).build());
      return;
    }

    // 对接正常。
    // 配置成功
    if (configResult.success()) {
      String oldRoomName = client.get(ROOM_KEY);
      // 新旧房间一样。
      if (!oldRoomName.equalsIgnoreCase(loginInfo.getRoomKey())) {
        // 离开原有
        ClientSimpleMessage leave = ClientSimpleMessage.builder()
          .roomKey(oldRoomName)
          .clientId(client.getSessionId().toString())
          .message(String.format("%s leave room %s", client.getSessionId().toString(), oldRoomName))
          .build();
        client.leaveRoom(oldRoomName);
        broadcastInRoom(client.get(ROOM_KEY), EVENT_LEAVE_ROOM, leave);

        // 加入新配置的
        client.set(ROOM_KEY, loginInfo.getRoomKey());
        client.joinRoom(loginInfo.getRoomKey());

        ClientSimpleMessage join = ClientSimpleMessage.builder()
          .roomKey(loginInfo.getRoomKey())
          .clientId(client.getSessionId().toString())
          .message(String.format("welcome %s to join room [%s]", client.getSessionId().toString(), loginInfo.getRoomKey()))
          .build();
        broadcastInRoom(loginInfo.getRoomKey(), EVENT_JOIN_ROOM, join);
      }
      ackRequest.sendAckData(InfoChangeResultVO.builder().success(true).message(configResult.getMessage()).build());

      // 配置失败
    } else {
      ackRequest.sendAckData(InfoChangeResultVO.builder().success(false).message(configResult.getMessage()).build());
    }


  }

  /**
   * 当配置更改时，维护配置。
   * 判断岸桥号是否变更。如果变更，需要切换到新的房间。
   */
  @OnEvent("publishMsg")
  public void publishMsg(SocketIOClient client, AckRequest ackRequest, ClientSimpleMessage simpleMessage) throws Exception {
    log.info("客户端: {}", client.getSessionId());
    log.info("msg: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(simpleMessage));
    broadcastInRoom(simpleMessage.getRoomKey(), EVENT_JOIN_ROOM, simpleMessage);
    ackRequest.sendAckData(InfoChangeResultVO.builder().success(true).message(simpleMessage.toString()).build());
  }


  @Override
  public void broadcastInRoom(String roomName, String eventName, Object message) {
    socketIoServer.getRoomOperations(roomName).sendEvent(eventName, message);
  }


  private void printClientInfo(SocketIOClient client) {
    log.info("onConnect ==> 客户端SessionId: {}", client.getSessionId());
    log.info("onConnect ==> 客户端roomKey: {}", client.getHandshakeData().getSingleUrlParam(ROOM_KEY));
    log.info("onConnect ==> 客户端getTransport: {}", client.getTransport());
    log.info("onConnect ==> 客户端getAllRooms: {}", client.getAllRooms());
    log.info("onConnect ==> 客户端getRemoteAddress: {}", client.getRemoteAddress());
  }
}
