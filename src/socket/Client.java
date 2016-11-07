package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private final int DEFAULT_PORT = 8585;
    private static final String DEFAULT_HOST = "localhost";

    public Client() {
        Scanner scan = new Scanner(System.in);
        try {

            socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Добро пожаловать в SchoolChat. " +
                    "\nОтправить сообщение конкретному пользователю: \"user_name<< текст сообщения\". " +
                    "\nПосмотреть все полученные вами приватные сообщения: printAll." +
                    "\nВыйти из чата: exit\n");
            System.out.println("Введите свой ник:");
            out.println(scan.nextLine());

            Receiver receiver = new Receiver();
            receiver.start();

            String message = "";
            while (!message.equals("exit")) {
                message = scan.nextLine();
                out.println(message);
            }
            receiver.stopReceive();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Ошибка при закрытии потоков");
        }
    }

    private class Receiver extends Thread {

        private boolean isRunning = true;

        public void stopReceive() {
            isRunning = false;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    String messageReceive = in.readLine();
                    System.out.println(messageReceive);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при получении сообщения.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
