package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final HostThreads THREADS = new HostThreads(5);;
    private boolean up;
    private Thread t;
    private ServerSocket server;
    private final int port;
    protected boolean isStopped = false;
    public static final long TIMEOUT = 1;//A minute.
    private static final byte[] RESPONSE_TIMEOUT = "{\"code\":429,\"text\":\"Слишком много запросов.\"".getBytes();

    public Server(int port) {
        if ((this.port = port) < 0 || port > 0xFFFF)
            throw new IllegalArgumentException("Значение порта вне допустимого предела: " + port);
    }

    public void start() throws IOException {
        if (server == null) {
            server = new ServerSocket();
            server.bind(new InetSocketAddress("localhost", port));
            
            synchronized (THREADS) {
                if (!up) {
                    up = true;
                    (t = new Thread(this)).start();
                }
            }
            System.out.println("Сервер запускается...");
        } else
            throw new IllegalArgumentException("Сервер уже запущен.");
    }

    public void close() throws Exception {
        if (!server.isClosed()) {
            server.close();
            synchronized (THREADS) {
                if (up) {
                    THREADS.close();
                    t.interrupt();
                    t = null;
                    up = false;
                }
            }
            server = null;
            System.out.println("Сервер закрывается...");
        } else
            throw new IllegalArgumentException("Сервер уже закрыт.");
    }

    public void run() {
        HostThreads.Connector c;
        while(true) {
            try {
                Socket client = server.accept();
                if ((c = THREADS.request(TIMEOUT)) == null) {
                    try (OutputStream out = client.getOutputStream()) {
                        out.write(RESPONSE_TIMEOUT);
                        out.flush();
                    }
                    continue;
                }
                c.start(client, THREADS);
            } catch (Throwable e) {
                break;
            }
            synchronized (THREADS) {
                if (!up) break;
            }
        }
    }

}

/*



    @Override
    public void run(){
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Сервер закрыт.") ;
                    return;
                }
                throw new RuntimeException("Ошибка при попытке соединения.", e);
            }
            new Thread(new LoginWork(clientSocket)).start();
        }
        System.out.println("Сервер закрыт.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при попытке закрыть сервер", e);
        }
    }

    private void openServerSocket() {
        System.out.println("Сервер запускается...");
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Не получается открыть порт " + this.serverPort, e);
        }
    }
}
*/