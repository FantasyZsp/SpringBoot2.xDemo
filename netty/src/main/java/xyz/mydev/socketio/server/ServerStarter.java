package xyz.mydev.socketio.server;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ZSP
 */
@Component
public class ServerStarter implements CommandLineRunner {
  private final SocketIOServer server;

  @Autowired
  public ServerStarter(SocketIOServer server) {
    this.server = server;
  }

  @Override
  public void run(String... strings) throws Exception {
    server.start();
  }
}
