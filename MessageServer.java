import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Scanner;

class MessageServer {
  
    static final int PORT = 31416;
    static volatile boolean[] readies;
    static String[] ips;
    static Queue<Socket> connections;


    public static void main(String[] args) throws IOException {
        connections = new ConcurrentLinkedQueue<>();

        new Thread(new AcceptConnections()).start();

        while (true) {
            for (Socket sock : connections) {
                if (sock.getInputStream().available() > 0) {

                    byte[] b = new byte[sock.getInputStream().available()];
                    sock.getInputStream().read(b);

                    for (Socket otherSocks : connections) {
                        if (otherSocks != sock) {
                            otherSocks.getOutputStream().write(b);
                            otherSocks.getOutputStream().flush();
                        }
                    }
                }
            }
        }
    }


    public static class AcceptConnections implements Runnable {

        public void run() {
            try {
                ServerSocket welcomeSocket = new ServerSocket(PORT);
                while (true) {
                    connections.offer(welcomeSocket.accept());
                }
            } catch (Exception e) { }
        }
    }

}
