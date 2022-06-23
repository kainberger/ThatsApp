package client;


import javafx.application.Platform;
import layout.login.LoginC;
import layout.register.RegisterC;
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


                //System.out.println("state:"+in.available());
                Object o = in.readObject();

                if (o instanceof TextMessage) {
                    msg = (TextMessage) o;
                    System.out.println("msg is "+msg);

                    switch (msg.getType()) {

                        case LOGIN:
                            Platform.runLater(new Runnable() {      //run on FX Thread
                                @Override
                                public void run() {
                                    LoginC.getController().openChat();
                                }
                            });
                            break;

                        case LOGIN_ERR:
                            //Run on FX Thread
                            Platform.runLater(new ClientRunErrorOnFx(msg.getMsg(), "login"));

                            break;

                        case REGISTRATION:

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    RegisterC.getController().openChat();
                                }
                            });
                            break;

                        case REGISTER_ERR:
                            //Run on FX Thread
                            Platform.runLater(new ClientRunErrorOnFx(msg.getMsg(),"register"));
                            break;

                        case STANDARD:

                            Platform.runLater(new ClientRunMsgOnFXThread(msg));
                            break;
                    }



                    // if(msg.getChat().equals(chat))
                    System.out.println("Message from: " + msg.getSrc().getName() + ": " + msg.getMsg());
                }
            }


        } catch (ConnectException ce) {
            Client.stop();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Everthing Stopped!");

        }

    }
}
