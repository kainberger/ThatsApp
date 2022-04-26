package client;

;

import muc.Message;
import muc.User;
import server.Server;

import java.io.*;
import java.net.Socket;

/**
 * client.Client Klasse
 */

import java.util.Scanner;

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

            sc.nextLine();

            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        try {
                            out.writeObject(new Message(msg, user, target));
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

                                if(target.equals(msg.getSrc()))
                                    System.out.println("Server : "+msg);
                            }

                            else if(msg != null)
                                msg = new Message("not a Message", null, null);

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
/*



package client;

import server.Server;

import java.io.*;
import java.net.Socket;

/**
 * client.Client Klasse
 *
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final Socket clientSocket; // socket used by client to send and recieve data from server
        final BufferedReader in;   // object to read data from socket
        final PrintWriter out;     // object to write data into socket
        final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
        try {
            clientSocket = new Socket("127.0.0.1", Server.PORT);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });

            sender.start();
            Thread receiver = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Server : "+msg);
                            msg = in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver .start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
*/