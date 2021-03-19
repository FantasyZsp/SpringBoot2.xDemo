package xyz.mydev.netty.practise.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 启动时direct buffer constructor: unavailable错误解决： https://stackoverflow.com/questions/57885828/netty-cannot-access-class-jdk-internal-misc-unsafe
 *
 * @author ZSP
 */
public class SimpleServer {

  public static void main(String[] args) throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
      .childHandler(new MyServerInitializer());

    try {
      ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
