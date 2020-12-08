package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import server.commands.AbstractCommand;

public class HostThreads implements java.io.Closeable{
    private final BlockingQueue<Connector> QUEUE;


    public HostThreads(int capacity) {
        QUEUE = new ArrayBlockingQueue<>(capacity);

        for(int i = -1; ++i < capacity; ) {
            try {
                Connector connector = new Connector();
                QUEUE.add(connector);
                new Thread(connector).start();
            }
            catch(Throwable e) {
                String name = Connector.class.getSimpleName() + "-" + i;
                System.out.format("Создать %s не удалось (%s)%n", name, e.getMessage());
            }
        }

        if(QUEUE.size() < 1)
            throw new UnsupportedOperationException("Не существует нитей для соединения с БД");
    }


    /**
     * @param timeout в секундах
     * @return Объект Connector или {@code null} если Connector недоступен.
     */
    // Запрос свободного соединения из очереди
    public Connector request(long timeout) {
        try {
            return QUEUE.poll(timeout, TimeUnit.SECONDS);
        }
        catch(Throwable e) {
            return null;
        }
    }

    //почти 100% вероятность, что true
    private boolean putBack(Connector c) {
        try {
            QUEUE.add(c);
        }
        catch(Throwable e) {
            return false;
        }
        return true;
    }


    @Override
    public void close() {
        for(Connector c : QUEUE)
            c.close();
    }



///////////////////////////    CLASS     CONNECTOR   /////////////////////////

    public static class Connector implements Runnable, AutoCloseable {
        private final AtomicBoolean CLOSED = new AtomicBoolean();
        private final Connection c;
        private HostThreads holder;
        private Socket client;
        private boolean active;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        String message;
        Object obj;


        private Connector()  throws Throwable {
            c = DBConnection.get();
        }

        public void start(Socket client, HostThreads holder) {
            //исключения
            if(holder == null)
                throw new UnsupportedOperationException("Исключение при попытке запустить нить:\n\tне передан владелец (null)");
            synchronized(c) {
                if(active)
                    throw new UnsupportedOperationException("Исключение при попытке запустить нить:\n\tнить уже запущена");
                if(client == null || client.isClosed())
                    throw new IllegalArgumentException("Исключение при попытке запустить нить:\n\tсоединение с клиентом завершено");
                // параметры
                this.client = client;
                this.holder = holder;

                //основное действие
                c.notify(); // см. run() ниже
                active = true;
            }
        }


        @Override
        public void run(){
            while(true)
            {
                try {
                    synchronized(c) {
                        active = false;
                        c.wait();
                    }
                } catch(Throwable e) { break; }


                System.out.println("Новый клиент: " + client.getRemoteSocketAddress());

                readMessage();
                operateMessage();
                System.out.println(obj);
                sendObject();

                while(!holder.putBack(this));
                if(CLOSED.get()) break;
            }
        }


        // Сообщение клиента — это "некотораяКоманда параметр1 параметр2 параметр3"
        // или "командаБезПараметров void"

        public void readMessage(){
            try {
                message = null;
                obj = null;
                oos = new ObjectOutputStream(client.getOutputStream());
                ois = new ObjectInputStream(client.getInputStream());
                message = (String) ois.readObject();

                if (message.equals("")) obj = ("Запрос пуст");
                else if (!message.contains("~")) obj = ("Запрос не содержит параметров");

            } catch (Exception e) {
                obj = "Ошибка при чтении данных"; e.printStackTrace();
            }
        }


        public void operateMessage(){
            AbstractCommand processor = Processor.COMMANDS.get(message.split("~", 2)[0]);
            if (processor == null && obj==null) obj = ("Команда не распознана");
            else if(processor!= null) try
            {
                Statement statement = c.createStatement();
                obj = processor.operate(message.split("~",2)[1], statement );
                //читает запрос к бд
                 //обработка полученных данных перед отправкой
                if (statement != null) {
                    statement.close();
                }
            }
            catch(IllegalArgumentException e) { obj = ("Команда не соответствует параметрам"); }
            catch(SQLException e){ obj = ("Ошибка базы данных"); }
        }


        public void sendObject(){
            try {
                oos.writeObject(obj);
                client.close();
                oos.close();
                ois.close();

            } catch(Throwable e) {
                e.printStackTrace();
            }
        }


        @Override
        public void close() {
            try { c.close(); }
            catch(Throwable e) {
                e.printStackTrace();
                return;
            }
            CLOSED.set(true);
        }

        public final boolean isActived() { synchronized(c) { return active; } }
        public final boolean isClosed() { return CLOSED.get(); }

    }


}
