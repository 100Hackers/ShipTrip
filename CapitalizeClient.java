import java.net.*;
import java.io.*;
import java.util.Scanner;

class CapitalizeClient {
  
  public static void main(String[] args) {
    String HOST = "john.cedarville.edu";//163.11.238.2";
    int PORT = 31416;
    try {
      Socket connection = new Socket(HOST, PORT);

      // Local
      InetSocketAddress localAddress = (InetSocketAddress) connection.getLocalSocketAddress();
      String localName = localAddress.getHostName();
      int localPort = localAddress.getPort();

      // Remote
      InetSocketAddress remoteAddress = (InetSocketAddress) connection.getRemoteSocketAddress();
      String remoteName = remoteAddress.getHostName();
      int remotePort = remoteAddress.getPort();

      // receive reply from server
      Scanner networkIn = new Scanner(connection.getInputStream());
      String messageIn = networkIn.nextLine();
      System.out.println("RCVD: " + messageIn);
      connection.close();


    } catch (IOException e) {
      e.printStackTrace();
    } 
  }  
}
