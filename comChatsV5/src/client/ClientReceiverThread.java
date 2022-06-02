package client;

import muc.KeyValuePair;
import muc.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.security.Key;
import java.util.LinkedList;
import java.util.List;

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

            Message msg;

            if (clientSocket.getInputStream().available() >= 0){
                in = new ObjectInputStream(clientSocket.getInputStream());
            }

            while (in.available() != -1) {      //While connected


                if (in.available() >= 0) {

                    Object o = in.readObject();
                    if (o instanceof Message) {
                        msg = (Message) o;
                        System.out.println(msg);
                    }
                    else if(o instanceof LinkedList && (((LinkedList<?>)o).size() > 0 && ((LinkedList<?>)o).get(0) instanceof KeyValuePair)) {
                        List<KeyValuePair> msgs = (LinkedList) o;
                        for (KeyValuePair kvp : msgs) {
                            System.out.println(kvp.getMessage());
                        }

                        //System.out.println(msgs);
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
