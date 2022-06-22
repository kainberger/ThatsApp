package client;

import layout.chat.ChatC;
import muc.*;
import server.ReceiverThread;
import server.SenderThread;

import javax.xml.soap.Text;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: send messages via Client.sendMsg();
//TODO: send register message to Server when registering

public class Client {
    public static Socket clientSocket; // socket used by client to send and recieve data from server
    public static ObjectInputStream in;   // object to read data from socket
    public static ObjectOutputStream out;     // object to write data into socket
    private static String username;
    private static ClientReceiverThread crt;
    public static User user;
    private static Chat chat;


    public static void connect() {
        try {
            clientSocket = new Socket("127.0.0.1", 4995);
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println("Connected to Server");

            crt = new ClientReceiverThread(Client.clientSocket);
            crt.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void login(String userName, String passwd) throws IOException, ThatsAppException {
        user = new User(userName);
        String loginMsg = userName + ";" + passwd;
        new ClientSenderThread(new TextMessage(loginMsg, null, user, Type.LOGIN), out).start();
        restoreCat();
    }



    public static void register(String username, String password, String email) throws ThatsAppException, IOException {
        String passwd = User.hashPassword(password);
        String registerMsg = username + ";" + passwd + ";" + email; //TODO: pw as hash

         user = new User(username);


        new ClientSenderThread(new TextMessage(registerMsg, null, user, Type.REGISTRATION), out).start();
    }



    /*public static void setTarget(User target) {
        Client.target = target;
        chat = new Chat(new LinkedList<>(Arrays.asList(user, target)));
    }

     */

    /* public static void sendMessage(String msg) throws IOException {
         new ClientSenderThread(new TextMessage(msg, chat, user, Type.STANDARD), out).start();
     }

     */
    public static void stop() {
        try {
                         //TODO: Fix exception
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Everythin closed!");
        }


    }

    public static void sendMsg(String msg, Chat chat, Type type) throws IOException {
        TextMessage message = new TextMessage(msg,chat,user,type);
        new ClientSenderThread(message,out).start();
        LocalCatalog.getInstance().add(message);
    }

    private static void restoreCat() {
        LocalCatalog.getInstance().restore();
    }

    public static void getMessages(List<User> chat, ChatC controller) {
        List<Message> msgs = LocalCatalog.getInstance().selectMsgsByChat(new Chat(chat));

        int i = 0;
        while(msgs != null && i < msgs.size()) {
            if(msgs.get(i).getSrc().equals(user) && msgs.get(i) instanceof TextMessage)  {
                TextMessage tmsg = (TextMessage) msgs.get(i);
                controller.showMessage(tmsg.getMsg());
            }
            else if(msgs.get(i) instanceof TextMessage) {
                TextMessage tmsg = (TextMessage) msgs.get(i);
                controller.showIncomingMsg(tmsg);
            }
            i++;
        }
    }
}
