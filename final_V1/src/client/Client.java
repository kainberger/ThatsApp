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

//TODO: send messages via Client.sendMsg();
//TODO: send register message to Server when registering

public class Client {
    public static Socket clientSocket; // socket used by client to send and recieve data from server
    public static ObjectInputStream in;   // object to read data from socket
    public static ObjectOutputStream out;     // object to write data into socket
    public static final Scanner sc = new Scanner(System.in); // object to read data from user's keybord
    private static String username;
    private static User user, target;
    private static Chat chat;

    public static void main(String[] args) {
        try {
            clientSocket = new Socket("127.0.0.1", Server.PORT);
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println("Connected to Server");

            //Login or Register

            /*System.out.println("Login[1] or Register[2]?");
            int choice = sc.nextInt();

            if(choice == 2) {

                //Register
                System.out.println("Registration!");
                System.out.print("Username: ");
                String userName = sc.next();
                System.out.println("Password: ");
                String passwd = sc.next();
                System.out.println("Email: ");
                String email = sc.next();

                String registerMsg = userName + ";" + passwd + ";" + email; //TODO: pw as hash

                 user = new User(userName);


                new ClientSenderThread(new TextMessage(registerMsg, null, user, Type.REGISTRATION), out).start();
            }
            else {
                System.out.println("Login!");
                System.out.println("Username: ");
                String userName = sc.next();
                System.out.println("Password:");
                String passwd = sc.next();

                 user = new User(userName);

                String loginMsg = userName + ";" +passwd;
                new ClientSenderThread(new TextMessage(loginMsg,null, user, Type.LOGIN),out).start();
            }*/

            String userName = args[0];
            String passwd = args[1];

            user = new User(userName);

            String loginMsg = userName + ";" +passwd;
            new ClientSenderThread(new TextMessage(loginMsg,null, user, Type.LOGIN),out).start();

            new ClientReceiverThread(clientSocket).start();

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
        } catch (IOException | ThatsAppException e) {
            e.printStackTrace();
        }
    }

    /*public static void register(String username, String password, String email) throws ThatsAppException, IOException {
        String registerMsg = username + ";" + password + ";" + email; //TODO: pw as hash

        User user = new User(username);


        new ClientSenderThread(new TextMessage(registerMsg, null, user, Type.REGISTRATION), out).start();
    }

     */

    /*public static void setTarget(User target) {
        Client.target = target;
        chat = new Chat(new LinkedList<>(Arrays.asList(user, target)));
    }

     */

   /* public static void sendMessage(String msg) throws IOException {
        new ClientSenderThread(new TextMessage(msg, chat, user, Type.STANDARD), out).start();
    }

    */
}
