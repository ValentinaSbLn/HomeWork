package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int DEFAULT_PORT = 8585;
    private final List<UserConnect> userCon = new CopyOnWriteArrayList<>();

    public Server() {
        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            while (true) {
                Socket socket = server.accept();
                UserConnect connect = new UserConnect(socket);
                userCon.add(connect);

                executorService.execute(connect);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            synchronized (userCon) {
                for (UserConnect anUserCon : userCon) {
                    anUserCon.close();
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при закрытии потоков");
        }
    }

    private class UserConnect extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;
        private List<String> privateChat;
        private String name;

        public UserConnect(Socket socket) {
            privateChat = new ArrayList<>();
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                name = in.readLine();
                synchronized (userCon) {
                    for (UserConnect user : userCon) {
                        user.out.println("System>> " + name + " joined the chat");
                    }
                }

                String str = "";

                while (true) {

                    str = in.readLine();

                    if (str.equals("exit")) break;

                    if (printAllPrivateMessage(str)) continue;

                    String[] privateMessage = str.split("<<");

                    synchronized (userCon) {
                        Iterator<UserConnect> iter = userCon.iterator();
                        while (iter.hasNext()) {

                            UserConnect user = iter.next();

                            if (!prvtMsg(privateMessage, user)) {
                                if (user.name.equals(this.name)) {
                                    this.out.println("You>> " + str);
                                } else
                                    user.out.println(name + ">> " + str);
                            }
                        }
                    }
                }

                synchronized (userCon) {
                    Iterator<UserConnect> iter = userCon.iterator();
                    while (iter.hasNext()) {
                        (iter.next()).out.println("system>> " + name + " lefted the chat");
                    }
                }
            } catch (IOException e) {
                System.out.println("Невозможно прочитать сообщение");
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();

                userCon.remove(this);

                if (userCon.isEmpty()) {
                    Server.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }
        }

        private boolean prvtMsg(String[] privateMessage, UserConnect user) {
            boolean isPrivate = false;

            if (privateMessage.length > 1) {
                isPrivate = true;
                if (privateMessage[0].equals(user.name)) {
                    user.out.println(name + " private >> " + privateMessage[1]);
                    synchronized (user.privateChat) {
                        user.privateChat.add(name + " said >> " + privateMessage[1]);
                    }
                }
            }
            return isPrivate;
        }

        private boolean printAllPrivateMessage(String str) {
            if (str.equals("printAll")) {
                out.println("Все сообщения для пользователя: ");
                for (String s : privateChat) {
                    out.println(s);
                }
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
