import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPSpyUsingTEAandPasswords {

    public static void main(String args[]) {

        // common utility
        CommonUtil common = new CommonUtil();


        Socket clientSocket = null;
        try {
            // arguments supply hostname
            int serverPort = 7777;
            clientSocket = new Socket(args[0], serverPort);

            // array to store message
            byte[] bytes = new byte[128];

            // length of message
            int length = 0;

            // read from server
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            // send byte[] to server
            DataOutputStream outbyte = new DataOutputStream(clientSocket.getOutputStream());

            // read from System.in
            System.out.println("Enter symmetric key for TEA:");
            Scanner scanner = new Scanner(System.in);

            // Input Symmetric key
            String key = scanner.nextLine();
            if (key == null || key.length() == 0) {
                System.out.println("The size of symmetric key cannot be zero");
                System.exit(0);
            }

            // create the TEA instance
            TEA tea = new TEA(key.getBytes());

            // Input ID
            System.out.println("Enter your ID");
            String id = scanner.nextLine();
            // encrypt id using tea
            // send it to sever
            outbyte.write(tea.encrypt(id.getBytes()));

            // Input password
            System.out.println("Enter your Password");
            String password = scanner.nextLine();
            // encrypt password using tea
            // send it to sever
            outbyte.write(tea.encrypt(password.getBytes()));

            // Input location
            System.out.println("Enter your location");
            String location = scanner.nextLine();
            // encrypt location using tea
            // send it to sever
            outbyte.write(tea.encrypt(location.getBytes()));

            // get the response message from sever
            length = in.read(bytes);
            // convert byte[] to string
            String responseMessage = common.getString(bytes, length);

            if (responseMessage.equals("IllegalKey")) {
                throw new Exception("Bad key");
            }

            if (responseMessage.equals("IDPassed")) {
                System.out.println(
                        "Thank you. Your location was securely transmitted to Intelligence Headquarters.");
            } else {
                System.out.println("Not a valid user-id or password.");
            }

        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception:" + e.getMessage());
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