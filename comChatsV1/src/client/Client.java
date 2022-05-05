package client;

import muc.Chat;
import muc.Message;
import muc.User;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

;

public class Client {
    public static void main(String[] args) {
        final Socket clientSocket; // socket used by client to send and recieve data from server
        final ObjectInputStream in;   // object to read data from socket
        final ObjectOutputStream out;     // object to write data into socket
        final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
        try {
            clientSocket = new Socket("127.0.0.1", Server.PORT);
            System.out.println("hi");

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            System.out.print("User: ");
            User user = new User(sc.next());
            System.out.print("Target: ");
            User target = new User(sc.next());
            Chat chat = new Chat(new LinkedList<User>(Arrays.asList(user, target)));

            sc.nextLine();

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
                                    System.out.println("Server : "+msg);
                            }
                        }while(msg!=null);

                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}