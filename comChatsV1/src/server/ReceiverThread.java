package server;

import muc.Chat;
import muc.Message;
import muc.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReceiverThread extends Thread{

    final private Socket clientSocket;
    private final ObjectInputStream in;
    private Message msg;
    private final HashMap<User, Socket> connectedUser;

    public ReceiverThread(Socket clientSocket, HashMap<User, Socket> connectedUser) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.connectedUser = connectedUser;
    }

    @Override
    public void run() {
        try {

            while(clientSocket.isConnected()){



                Object o = in.readObject();     //read Objects

                if(o instanceof Message) {
                    msg = (Message) o;

                    System.out.println("Server received from "+msg.getSrc().getName() + " msg: "+msg);

                    //check if it´s login msg
                    if (msg.getChat() == null){
                        connectedUser.put(new User(msg.toString()),clientSocket);
                    }

                    else {
                        //start new SenderThread that sends msg to all connected clients
                        new SenderThread(msg, getSockets(msg.getChat())).start();
                    }



                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Socket> getSockets(Chat chat){

        List<Socket> sockets= new LinkedList<>();

        //Get all Sockets from Users in Chat

        for(User u: chat.getUsers()){
            if(connectedUser.containsKey(u)) {
                sockets.add(connectedUser.get(u));
            }
        }

        return sockets;
    }

}
