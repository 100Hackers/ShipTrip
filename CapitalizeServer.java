import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

class CapitalizeServer {
  
  static final int PORT = 31416;
  static volatile boolean[] readies;
  static String[] ips;

  public static void main(String[] args) throws IOException {
    readies = new boolean[2];
    readies[0] = false;
    readies[1] = false;

    ips = new String[2];


    ServerSocket welcomeSocket = new ServerSocket(PORT);
    
    Thread t0 = new Thread(new ServedUP(0, welcomeSocket.accept()));
    Thread t1 = new Thread(new ServedUP(1, welcomeSocket.accept()));

    t0.start();
    t1.start();

    t0.yield();
    t1.yield();

  }

  public static class ServedUP implements Runnable {
    int id;
    Socket socket;

    public ServedUP(int d, Socket s) {
        id = d;
        socket = s;
    }

    public void run() {

        try {
            if (id == 1) while(!readies[0]);
            System.out.println(id + " " + "starting");
            PrintStream pstream = new PrintStream(socket.getOutputStream());
          /*welcomeSocket= new ServerSocket();
         welcomeSocket.setReuseAddress(true);
         welcomeSocket.bind(new InetSocketAddress(PORT));
          *///Socket connection = socket.accept();      
            System.out.println(id + " " + "connected");

          InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
          String remoteName = remoteAddress.getHostName();
            System.out.println(id + " " + remoteName);


          ips[id] = remoteName;
          readies[id] = true;
          
          System.out.println(id + " " + "waiting");

          while(!readies[1-id]);

        System.out.println(id + " " + "made it out");
          PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
          out.println(ips[1-id]);
          System.out.println(id + " " + "sending ip");
          
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

  }
}
