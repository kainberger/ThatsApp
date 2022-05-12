package client;

import muc.*;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {

        final Socket clientSocket; // socket used by client to send and recieve data from server
        final ObjectInputStream in;   // object to read data from socket
        final ObjectOutputStream out;     // object to write data into socket
        final Scanner sc = new Scanner(System.in); // object to read data from user's keybord


        try {

            clientSocket = new Socket("127.0.0.1", Server.PORT);
            out = new ObjectOutputStream(clientSocket.getOutputStream());


            System.out.println("Connected to Server");


            System.out.print("User: ");
            User user = new User(sc.next());
            //Login


            new ClientSenderThread(new Message("", null, user), out).start();

            new ClientReceiverThread(clientSocket).start();
            System.out.print("Target: ");
            User target = new User(sc.next());
            Chat chat = new Chat(new LinkedList<>(Arrays.asList(user, target)));

            System.out.println("Message: ");
            String msg = sc.next();


            new ClientSenderThread(new Message(msg, chat, user), out).start();

            /*Thread sender = new Thread(new Runnable() {
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
            sender.start();*/
            /*in = new ObjectInputStream(clientSocket.getInputStream());

            Thread receiver = new Thread(new Runnable() {
                Message msg;

                @Override
                public void run() {
                    try {

                        while (clientSocket.isConnected()) {

                            Object o = in.readObject();

                            if (o instanceof Message) {
                                msg = (Message) o;

                                // if(msg.getChat().equals(chat))
                                System.out.println("Message from: " + msg.getSrc().getName() + ": " + msg.getMsg());
                            }
                        }

                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
