package client;

import muc.Message;
import muc.TextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;

public class ClientReceiverThread extends Thread {

    private ObjectInputStream in;
    private final Socket clientSocket;

    public ClientReceiverThread(Socket clientSocket) throws IOException {
       // this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {

            TextMessage msg;

            if (clientSocket.getInputStream().available() >= 0){
                in = new ObjectInputStream(clientSocket.getInputStream());
            }

            while (in.available() != -1) {      //While connected


                if (in.available() >= 0) {

                    Object o = in.readObject();

                    if (o instanceof TextMessage) {
                        msg = (TextMessage) o;

                        // if(msg.getChat().equals(chat))
                        System.out.println("Message from: " + msg.getSrc().getName() + ": " + msg.getMsg());
                    }
                }
            }

            System.out.println("Server out of service");
            in.close();
            clientSocket.close();
        }catch (ConnectException ce){

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
