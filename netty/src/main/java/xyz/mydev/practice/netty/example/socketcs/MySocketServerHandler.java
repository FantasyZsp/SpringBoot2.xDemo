package xyz.mydev.practice.netty.example.socketcs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author ZSP
 */
@Slf4j
public class MySocketServerHandler extends SimpleChannelInboundHandler<String> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    log.info("server receive remote {}  info : {}", ctx.channel().remoteAddress(), msg);
    UUID uuid = UUID.randomUUID();
    ctx.channel().writeAndFlush("from server: " + uuid.toString());

  }

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelRegistered");
    super.channelRegistered(ctx);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelUnregistered");
    super.channelUnregistered(ctx);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelActive");
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelInactive");
    super.channelInactive(ctx);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    System.out.println("channelReadComplete");
    super.channelReadComplete(ctx);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    System.out.println("userEventTriggered");
    super.userEventTriggered(ctx, evt);
  }

  @Override
  public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
    log.info("channelWritabilityChanged");
    super.channelWritabilityChanged(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.info("exceptionCaught");
    super.exceptionCaught(ctx, cause);
  }

  @Override
  protected void ensureNotSharable() {
    log.info("ensureNotSharable");
    super.ensureNotSharable();
  }

  @Override
  public boolean isSharable() {
    log.info("isSharable");
    return super.isSharable();
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    log.info("handlerAdded");
    super.handlerAdded(ctx);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    log.info("handlerRemoved");
    super.handlerRemoved(ctx);
  }
}
