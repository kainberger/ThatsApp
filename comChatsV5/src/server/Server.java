package server;

import muc.KeyValuePair;
import muc.Message;
import muc.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Server Klasse
 */


public class Server {
    public final static int PORT = 4997;

    public static HashMap<User, Socket> connectedUsers = new HashMap<>();
    public static HashMap<Socket, ObjectOutputStream> outputstreams = new HashMap<>();
    public static List<Socket> clients = new LinkedList<>();
    //public static List<User> users = new LinkedList<>();
    //public static HashMap<KeyValuePair, Message> undeliveredMsgs = new HashMap<>();
    public static List<KeyValuePair> undeliveredMsgs = new LinkedList<>();


    public static void main(String[] args) {

        ServerSocket serverSocket;
        //ServerCheckerThread serverCheckerThread = new ServerCheckerThread();
        //serverCheckerThread.start();


        //final Scanner sc = new Scanner(System.in);


        try {
            serverSocket = new ServerSocket(PORT);

            /*ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                for (KeyValuePair kvp : Server.undeliveredMsgs) {
                    if(Server.connectedUsers.containsKey(kvp.getUser())) {
                        List<Socket> helper = new LinkedList<>();
                        helper.add(Server.connectedUsers.get(kvp.getUser()));
                        try {
                            new ServerSenderThread(kvp.getMessage(), helper).start();
                            Server.undeliveredMsgs.remove(kvp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("went through " + kvp);
                }
                System.out.println("Checked");
            }, 10, 5, TimeUnit.SECONDS);*/

            System.out.println("Server runnning on " + PORT);


            while (true) {
                //System.out.println(exec.isTerminated());
                Socket clientSocket = serverSocket.accept();

                clients.add(clientSocket);
                outputstreams.put(clientSocket, new ObjectOutputStream(clientSocket.getOutputStream()));
                System.out.println("Connection accepted!");


                new ServerReceiverThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
