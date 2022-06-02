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

//done ==>
//_TODO: msgs sent only every 5 Seconds - why? ServerCheckerThread stops once ServerSenderThread is created
//_TODO: thoughts: you don't need to check every x seconds for undelivered messages - just send them to the client once they have sucessfully logged in

//TODO: only give user his own messages


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

            while(true) {
                System.out.print("Message: ");
                String msg = sc.nextLine();


                if(msg.trim().length() > 0)
                    new ClientSenderThread(new Message(msg, chat, user), out).start();
            }

        } catch (IOException | ThatsAppException e) {
            e.printStackTrace();
        }

    }
}