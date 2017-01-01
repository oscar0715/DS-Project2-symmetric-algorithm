import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

class Connection extends Thread {
    // socket
    private Socket clientSocket;

    // DataStream used to read from client and write to client
    private DataInputStream in;
    private DataOutputStream out;

    // TEA class used to decrypt and encrypt
    private TEA tea;

    // count of clients
    private int count;

    // common utility
    private CommonUtil commonUtil;
    private KMLWriter kmlWriter;

    // map store clients's id and password
    private Map<String, String> spyMap;

    // used to hash the password
    private PasswordHash hash;
    private String salt;

    public Connection(Socket aClientSocket,
                             TEA tea,
                             int count,
                             Map<String, String> map,
                             KMLWriter writer) {
        try {
            this.clientSocket = aClientSocket;
            this.in = new DataInputStream(clientSocket.getInputStream());
            this.out = new DataOutputStream(clientSocket.getOutputStream());

            this.tea = tea;
            this.count = count;

            this.commonUtil = new CommonUtil();
            this.kmlWriter = writer;
            this.spyMap = map;

            this.hash = new PasswordHash();
            this.salt = "asdgaskjbg5234ewgrhmlf213klnonfeoiwa";

            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            // byte array to store received messgae;
            byte[] bytes = new byte[128];

            // to store the length of received message;
            int length = 0;

            // get id
            length = in.read(bytes);
            String id = commonUtil.getStringAfterDecrypt(tea, bytes, length);



            // get password
            length = in.read(bytes);
            String password = commonUtil.getStringAfterDecrypt(tea,
                    bytes,
                    length);
            password = hash.getHashValue(salt + password);

            // get location
            length = in.read(bytes);
            String location = commonUtil.getStringAfterDecrypt(tea,
                    bytes,
                    length);

            // key Validation
            if (!commonUtil.keyValidation(location)) {
                out.write("IllegalKey".getBytes());
                System.out.println("Got visit " + count
                                           + " illegal symmetric key used. This may be an attack.");
                return;
            }

            if (!(spyMap.containsKey(id) && spyMap.get(id).equals(password))) {
                out.write("IDNotPass".getBytes());
                System.out.print("Got visit " + count + " from " + id);
                System.out.println(
                        ". Illegal Password attempt. This may be an attack.");
                return;
            }

            // location management
            // the path of the kml file is "./src/SecretAgents.kml"
            out.write("IDPassed".getBytes());
            kmlWriter.change(id, location);
            kmlWriter.writeToFile();
            System.out.println("Got visit " + count + " from " + id);

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/}
        }
    }

}
