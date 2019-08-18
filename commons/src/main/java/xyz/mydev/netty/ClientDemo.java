package xyz.mydev.netty;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class ClientDemo {
  public static void main(String[] args) throws URISyntaxException {

    IO.Options options = new IO.Options();
    options.transports = new String[]{"websocket"};
    options.reconnectionAttempts = 2;
    options.reconnectionDelay = 1000;
    options.timeout = 500;
    final Socket socket = IO.socket("http://localhost:5004");

//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                socket.send("authentication");
//            }
//        });

    socket.on("listenPoolList", new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        System.out.println("getConnectionsgetConnectionsgetConnections");
        for (Object o : args) {
          System.out.println(o);
        }
        socket.emit("authentication", "AAAAAAAAAAA,BBBBBBBBBB");
      }
    });

//        socket.on("listenPoolList", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                System.out.println("listenPoolList");
////                socket.send("authentication");
//            }
//        });

//        socket.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                socket.send("EVENT_MESSAGE");
//            }
//        });

    socket.connect();
    System.out.println("==========================");

//        socket.send("authentication");
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            String message = sc.next();
//            System.out.println( "client console send data="+message);
//
//            socket.send(message);
//        }


  }
}
