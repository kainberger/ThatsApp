package client;


import javafx.application.Platform;
import layout.chat.ChatC;
import layout.login.LoginC;
import layout.register.RegisterC;
import muc.Message;
import muc.TextMessage;
import muc.Type;

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
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {

            TextMessage msg;


            while (clientSocket.isConnected()) {      //While connected


                    Object o = in.readObject();

                    if (o instanceof TextMessage) {
                        msg = (TextMessage) o;

                        if (msg.getType() == Type.ERROR){
                            RegisterC.err = true;
                            RegisterC.errorMsg = msg.getMsg();
                            LoginC.err = true;
                            LoginC.errorMsg = msg.getMsg();
                        }

                        if (msg.getType() == Type.STANDARD){
                            Platform.runLater(new ClientRunOnFXThread(msg));
                        }


                        // if(msg.getChat().equals(chat))
                        System.out.println("Message from: " + msg.getSrc().getName() + ": " + msg.getMsg());
                    }
                }





        }catch (ConnectException ce){
            Client.stop();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Everthing Stopped!");

        }

    }
}
