import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TCPSpyCommanderUsingTEAandPasswords {

    public static void main(String args[]) {
        // common utility
        Map<String, String> spyMap = new HashMap<>();
        KMLWriter writer = new KMLWriter();
        // initialize the kml file
        writer.writeToFile();

        int count = 0;

        // initialize (temporary solution)
        spyMap.put("jamesb", "7FCDDD6191C985E41BFBF9C374D20A6D290FDF3E");
        spyMap.put("joem", "EA12B7C9145F5B80694ED353DB54ED2E046CAFC0");
        spyMap.put("mikem", "C9FB373B1AEA13EF6BDD58A77E39EE9432D8F3E3");
        spyMap.put("sean", "2384453C388CF0231302C23B9C7103865B6707F3");

        Socket clientSocket = null;
        try {

            // Ask to Input a symmetric key
            System.out.println("Enter symmetric key for TEA:");

            // scanner used to from system in
            Scanner scanner = new Scanner(System.in);

            // Read the key from the System.in
            String inputKey = scanner.nextLine();

            TEA tea = new TEA(inputKey.getBytes());

            System.out.println("Waiting for spies to visit...");

            // the server port we are using
            int serverPort = 7777;

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making 
             * the socket ready for reading and writing.
             */
            while (true) {
                clientSocket = listenSocket.accept();
                // If we get here, then we are now connected to a client.

                // create a thead fot this user
                Connection c = new Connection(clientSocket,
                                                     tea,
                                                     ++count,
                                                     spyMap, writer);
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }
}
