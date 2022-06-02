package server;

import muc.Chat;
import muc.KeyValuePair;
import muc.Message;
import muc.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerReceiverThread extends Thread {

    final private Socket clientSocket;
    private final ObjectInputStream in;

    public ServerReceiverThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        Message msg;
        User user = null;
        try {

            while (in.available() != -1) {  //While connected

                Object o = in.readObject();     //read Objects

                if (o instanceof Message) {
                    msg = (Message) o;

                    //check if it´s login msg
                    if (msg.getChat() == null) {
                        user = msg.getSrc();
                        Server.connectedUsers.put(user, clientSocket);
                        //send list of undelivered Messages to client

                        Server.outputstreams.get(clientSocket).writeObject(getKVPsForUser(user));
                        //System.out.println(getKVPsForUser(user));

                    } else {
                        System.out.println("Message received: " + msg);
                        //start new SenderThread that sends msg to all connected clients
                        new ServerSenderThread(msg, getSockets(msg)).start();
                    }


                }
            }

            clientSocket.close();


        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client Disconnected");  //if disconnected

            Server.clients.remove(clientSocket);
            Server.outputstreams.remove(clientSocket);
            if (user != null) {
                Server.connectedUsers.remove(user);
            }
        }
    }

    private List<Socket> getSockets(Message msg) {

        List<Socket> sockets = new LinkedList<>();

        //Get all Sockets from Users in Chat
        for (User u : msg.getChat().getUsers()) {
            if (Server.connectedUsers.containsKey(u)) {
                sockets.add(Server.connectedUsers.get(u));
            }
            else {
                //Server.undeliveredMsgs.put(new KeyValuePair(u, msg), msg);
                Server.undeliveredMsgs.add(new KeyValuePair(u, msg));
                System.out.println(Server.undeliveredMsgs);
            }

        }
        return sockets;
    }

    private List<KeyValuePair> getKVPsForUser(User user) {
        List<KeyValuePair> helper = new LinkedList<>();
        for (KeyValuePair kvp : Server.undeliveredMsgs) {
            if(kvp.getUser().equals(user)) {
                helper.add(kvp);
            }
        }
        return helper;
    }
}