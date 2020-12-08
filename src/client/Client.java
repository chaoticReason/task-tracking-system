package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static Client client;
    private final int PORT = 1880;

    public static void start(){
        if(client == null)
            client = new Client();
    }
    public static Client get(){
        return client;
    }

    public synchronized Object sendMessage(String message){
        Object obj;
        try {
            Socket socket = new Socket("localhost", PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(message);
            obj = ois.readObject();

            oos.close();
            ois.close();
            socket.close();

            return obj;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
