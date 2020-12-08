package server;
import server.Server;

public class StartServer {
    public static final int PORT_WORK = 1880;

    public static void main(String[] args) {
        Server server = new Server(PORT_WORK);
        try{
            server.start();
        }
        catch(Exception e){ //сейчас есть необработанные ошибки
            e.printStackTrace();
            e.getCause();
        }

    }
}