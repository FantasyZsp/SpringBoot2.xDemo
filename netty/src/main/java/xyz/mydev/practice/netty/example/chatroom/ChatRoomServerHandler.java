package xyz.mydev.practice.netty.example.chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author ZSP
 */
public class ChatRoomServerHandler extends SimpleChannelInboundHandler<String> {

  private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  /**
   * 广播消息，同时处理身份信息
   */
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.forEach(ch -> {
      if (ch != channel) {
        ch.writeAndFlush(channel.remoteAddress() + " 发送的消息：" + msg + "\n");
      } else {
        ch.writeAndFlush("【自己】" + msg + "\n");
      }
    });
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("【服务器】" + channel.remoteAddress() + "加入，当前人数：" + (channelGroup.size() + 1) + "\n");
    channelGroup.add(channel);
  }

  /**
   * 这里不需要 channelGroup.remove(channel)
   */
  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("【服务器】" + channel.remoteAddress() + "离开，当前人数：" + channelGroup.size() + "\n");
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println("【服务器】" + channel.remoteAddress() + "上线，当前人数：" + channelGroup.size() + "\n");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println("【服务器】" + channel.remoteAddress() + "下线，当前人数：" + channelGroup.size() + "\n");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
