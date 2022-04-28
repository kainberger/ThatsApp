package server;

import muc.Chat;
import muc.Message;
import muc.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Server Klasse
 */


public class Server {
    public final static int PORT = 4999;
    public static void main(String[] args) {
        ServerSocket serverSocket;
        final Scanner sc = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.print("User: ");
            User user = new User(sc.nextLine());
            System.out.print("Target: ");
            User target = new User(sc.nextLine());
            Chat chat = new Chat(new LinkedList<User>(Arrays.asList(user, target)));

            while (true) {
                ObjectOutputStream out;
                ObjectInputStream in;
                Socket clientSocket;

                clientSocket = serverSocket.accept();

                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                Thread sender = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run() {
                        while(true){
                            msg = sc.nextLine();
                            try {
                                out.writeObject(new Message(msg, chat, user));
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                sender.start();


                Thread receiver = new Thread(new Runnable() {
                    Message msg;
                    @Override
                    public void run() {
                        try {
                            do {
                                Object o = in.readObject();

                                if(o instanceof Message) {
                                    msg = (Message) o;

                                    if(msg.getChat().equals(chat))
                                        System.out.println("Server: ID: "+msg.getSrc().getID() + " msg: "+msg);
                                }

                            }while(msg!=null);

                            System.out.println("Server out of service");
                            out.close();
                            clientSocket.close();
                            serverSocket.close();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                receiver.start();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}