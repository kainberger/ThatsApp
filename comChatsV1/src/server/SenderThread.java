package server;

import muc.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class SenderThread extends Thread{

    private Message msg;
    private final List<Socket> clients;

    public SenderThread(Message msg, List<Socket> clients) throws IOException {
        this.msg = msg;
        this.clients = new LinkedList<>(clients);
    }

    @Override
    public void run() {

        ObjectOutputStream out;

        //loop through all Sockets and send message
        for (Socket s : clients) {
            if(s.isConnected() && s != Server.connectedUsers.get(msg.getSrc())){
                try {
                    out = Server.outputstreams.get(s);
                    out.writeObject(msg);
                    out.flush();
                    System.out.println("Sent Message to Client: ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
