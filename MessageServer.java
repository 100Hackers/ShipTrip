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
            Iterator<Socket> i = connections.iterator();

            while (i.hasNext()) {
                Socket sock = i.next();
                int count = 0;
                try {
                    count = sock.getInputStream().available();
                } catch (Exception e) {
                    i.remove();
                    break;
                }

                if (count > 0) {
                    byte[] b = new byte[count];
                    try {
                        sock.getInputStream().read(b);
                    } catch (Exception e) {
                        i.remove();
                        break;
                    }

                    Iterator<Socket> j = connections.iterator();

                    while (j.hasNext()) {
                        Socket otherSocks = j.next();
                        try {
                            if (otherSocks != sock) {
                                otherSocks.getOutputStream().write(b);
                                otherSocks.getOutputStream().flush();
                            }
                        } catch (Exception e) {
                            j.remove();
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
