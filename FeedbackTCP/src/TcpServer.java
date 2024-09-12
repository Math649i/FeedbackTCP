import java.io.*;
import java.net.*;

public class TcpServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Modtag filnavn
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String fileName = dis.readUTF();
                System.out.println("Receiving file: " + fileName);

                // Opret en ny fil på serveren
                FileOutputStream fos = new FileOutputStream("received_" + fileName);

                // Buffer for at læse fildata
                byte[] buffer = new byte[4096];
                int bytesRead;

                // Læs data fra klienten og skriv til filen
                while ((bytesRead = dis.read(buffer)) > 0) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.close();
                System.out.println("File " + fileName + " received");

                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
