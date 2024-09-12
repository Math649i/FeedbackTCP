import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file path to send: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist!");
            return;
        }

        try (Socket socket = new Socket(hostname, port)) {
            // Send filnavn til serveren
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(file.getName());

            // Læs filen og send den til serveren
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Send filindholdet til serveren
            while ((bytesRead = fis.read(buffer)) > 0) {
                dos.write(buffer, 0, bytesRead);
            }

            fis.close();
            System.out.println("File " + file.getName() + " sent to the server");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
