package xyz.mydev.practice.netty.example.socketcs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ZSP
 */
public class SimpleSocketClient {

  public static void main(String[] args) throws Exception {
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    Bootstrap clientBootstrap = new Bootstrap();
    clientBootstrap.group(workerGroup).channel(NioSocketChannel.class)
      .handler(new MySocketClientInitializer());
    try {
      ChannelFuture channelFuture = clientBootstrap.connect("localhost", 9998).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}