import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        try {
            InetAddress serverInetAddress;
            if (args.length > 1) {
                serverInetAddress = InetAddress.getByName(args[0]);
            } else {
                serverInetAddress = InetAddress.getLocalHost();
            }
            Socket socket = new Socket(serverInetAddress, 12000);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

            String welcomeMessage = br.readLine();
            System.out.println("Server says: " + welcomeMessage);


            while (true) {
                String line = bReader.readLine();
                if (line == null) {
                    break;
                }
                bw.write(line + System.lineSeparator());
                bw.flush();
                String received = br.readLine();
                System.out.printf("Sent: %s%nReceived: %s%n", line, received);
                if (received == null || received.equals("Terminating connection.")) {
                    break;
                }
            }

            bw.flush();
            socket.close();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}