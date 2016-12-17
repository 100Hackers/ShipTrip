import java.net.*;
import java.io.*;
import java.util.Scanner;

class MessageClient {

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

            BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

            // receive reply from server
            while (true) {
                if (connection.getInputStream().available() > 0) {
                    byte[] b = new byte[connection.getInputStream().available()];
                    connection.getInputStream().read(b);
                    System.out.println(new String(b));
                }

                if (scan.ready()) {
                    connection.getOutputStream().write((scan.readLine()+"\n").getBytes());
                    connection.getOutputStream().flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }  
}
