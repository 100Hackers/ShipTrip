import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

class CapitalizeServer {
  
  static boolean[] readies;
  static String[] ips;

  public static void main(String[] args) {
    readies = new boolean[2];
    readies[0] = false;
    readies[1] = false;

    ips = new String[2];


    ServerSocket welcomeSocket;
    
    Thread t0 = new Thread(new ServedUP(0, readies));
    Thread t1 = new Thread(new ServedUP(1, readies));


    t0.start();
    t1.start();

    t0.yield();
    t1.yield();

  }

  public static class ServedUP implements Runnable {
    int id;
     volatile boolean[] reads;

    public ServedUP(int d, boolean[] rs) {
        id = d;
        reads = rs;
    }

    public void run() {
        ServerSocket welcomeSocket;
    int PORT = 31416;

        try {
            if (id == 1) while(!reads[0]);
            System.out.println(id + " " + "starting");
          welcomeSocket= new ServerSocket();
         welcomeSocket.setReuseAddress(true);
         welcomeSocket.bind(new InetSocketAddress(PORT));
          Socket connection = welcomeSocket.accept();      
            System.out.println(id + " " + "connected");

          InetSocketAddress remoteAddress = (InetSocketAddress) connection.getRemoteSocketAddress();
          String remoteName = remoteAddress.getHostName();
            System.out.println(id + " " + remoteName);


          ips[id] = remoteName;
          reads[id] = true;
          
          System.out.println(id + " " + "waiting");

          while(!reads[1-id]);

        System.out.println(id + " " + "made it out");
          PrintWriter out = new PrintWriter(connection.getOutputStream(),true);
          out.println(ips[1-id]);
          System.out.println(id + " " + "sending ip");
          
          connection.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

  }
}
