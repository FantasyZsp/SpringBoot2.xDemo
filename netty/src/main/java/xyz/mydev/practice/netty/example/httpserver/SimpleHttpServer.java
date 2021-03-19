package xyz.mydev.practice.netty.example.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * java11 启动时direct buffer constructor: unavailable错误解决： https://stackoverflow.com/questions/57885828/netty-cannot-access-class-jdk-internal-misc-unsafe
 * <p>增加jvm参数
 * -Dio.netty.tryReflectionSetAccessible=true --add-opens java.base/jdk.internal.misc=ALL-UNNAMED
 *
 * @author ZSP
 */
public class SimpleHttpServer {

  public static void main(String[] args) throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
      .childHandler(new MyHttpServerInitializer());

    try {
      ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
