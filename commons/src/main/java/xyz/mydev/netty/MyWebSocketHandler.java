package xyz.mydev.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

  private WebSocketServerHandshaker webSocketServerHandshaker;

  private static final String WEB_SOCKET_URL = "ws://localhost:8888/websocket";

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

  }

  // 客户端与服务端创建连接的时候调用
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    NettyConfig.group.add(ctx.channel());
    System.out.println("连接开启。");

//        super.channelActive(ctx);
  }


  // 断开连接的时候调用
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    NettyConfig.group.remove(ctx.channel());
    System.out.println("连接关闭。");
  }


  // 接收客户端数据结束之后
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
