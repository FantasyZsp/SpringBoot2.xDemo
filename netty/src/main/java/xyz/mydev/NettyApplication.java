package xyz.mydev;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author ZSP
 */
public class NettyApplication {
  public static void main(String[] args) {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
  }
}
