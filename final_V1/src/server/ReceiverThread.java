package server;

import muc.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class ReceiverThread extends Thread {
    private final LocalDateTime time = LocalDateTime.now();

    final private Socket clientSocket;
    private final ObjectInputStream in;

    private User user = null;
    private TextMessage msg;

    public ReceiverThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {

        try {

            while (clientSocket.isConnected()) {  //While connected

                Object o = in.readObject();     //read Objects

                if (o instanceof Message) {
                    msg = (TextMessage) o;
                    System.out.println(time + " Server received from " + msg.getSrc().getName() + " msg: " + msg);


                    //check msg type
                    //

                    switch (msg.getType()) {

                        case REGISTRATION:
                            registerUser(msg.getMsg());     //register new User

                            break;
                        case LOGIN:
                            loginUser(msg.getMsg());    //Login User

                            break;
                        case LOGOUT:
                            logoutUser();
                            break;
                        case STANDARD:          //Send message
                            new SenderThread(msg, getSockets(msg.getChat())).start();
                            //about undelivered msgs


                    }





                        /*user = isRegistered(msg.getMsg());

                        if(user == null){       //!
                            System.out.println("Not Registered yet!");
                            new SenderThread(new Message("Register Please!!",null,Server.SRVUser), Arrays.asList(clientSocket)).start();
                        }

                        else {*/


                    // }

                    //start new SenderThread that sends msg to all connected clients


                }
            }

            clientSocket.close();


        } catch (IOException | ClassNotFoundException e) {

            System.err.println("Client Disconnected");  //if disconnected
            logoutUser();

            Server.outputstreams.remove(clientSocket);

        } catch (ThatsAppException e) {
            System.out.println("User Registration: " + e.getMessage());
            try {
                sendResponseMsg(e.getMessage(),Type.ERROR);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Get all Sockets of connected Users in Chat
    private List<Socket> getSockets(Chat chat) {

        List<Socket> sockets = new LinkedList<>();

        //Get all Sockets from Users in Chat
        System.out.println("connected User: " + Server.connectedUsers);
        for (User u : chat.getUsers()) {
            //  System.out.println("user: " + u.toString());
            if (Server.connectedUsers.containsKey(u)) {
                sockets.add(Server.connectedUsers.get(u));
            } else {
                //save to unsent messages
                Server.undeliveredMsgs.add(msg);
                System.out.println("added " + msg);
            }
        }

        return sockets;
    }

    //Check if user is already registered
    private Boolean isRegistered(String username) {
        User user = UserCatalog.getInstance().getUserbyName(username);
        return user != null;
    }

    //Register new User
    private void registerUser(String userString) throws ThatsAppException, IOException, InterruptedException {

        User newUser;
        String name;
        String passwordHash;
        String email;
        name = userString.split(";", 3)[0];
        passwordHash = userString.split(";", 3)[1];
        email = userString.split(";", 3)[2];

        newUser = new User(name, passwordHash, email,true); //TODO: pw as hash

        if (!isRegistered(newUser.getName())) {
            UserCatalog.getInstance().addUser(newUser);
            UserCatalog.getInstance().persist();

            System.out.println("Successfully registered!");
            System.out.println(UserCatalog.getInstance());

            user = newUser; //add to thread for identification

            Server.connectedUsers.put(newUser, clientSocket);
            System.out.println("\nconnected Users: " + Server.connectedUsers);

            //send success msg

            sendResponseMsg("Successfully registered!!!",Type.REGISTRATION);
            deliverMsgs(user);

        } else {
            //Error Response Msg
            sendResponseMsg("Registration failed! User already registered!",Type.REGISTER_ERR);
        }

    }

    private void sendResponseMsg(String msg, Type type) throws IOException {
        TextMessage errorMsg = new TextMessage(msg, null, Server.SRVUser, type);
        new SenderThread(errorMsg, Collections.singletonList(this.clientSocket)).start();
    }

    private void loginUser(String login) throws IOException, ThatsAppException, InterruptedException {
        String userName;
        String passwd;

        userName = login.split(";", 2)[0];
        passwd = login.split(";", 2)[1];


        if (isRegistered(userName)) {
             user = UserCatalog.getInstance().getUserbyName(userName);
            if (Objects.equals(user.getName(), userName) && Objects.equals(user.getPasswordHash(), User.hashPassword(passwd))) {
                Server.connectedUsers.put(user, clientSocket);

                System.out.println("Successfully Logged In!");
                System.out.println("Connected User: " + Server.connectedUsers);

                //send success Msg
              //  sendResponseMsg("Successfully logged in!!!");
                sendResponseMsg("Sucessfully Logged in!",Type.LOGIN);
                deliverMsgs(user);
            } else {
                //send error response Msg
                sendResponseMsg("Login failed",Type.LOGIN_ERR);
            }

        } else {
            // Send error response Msg
            sendResponseMsg("Login failed!!! User not registered!!!", Type.LOGIN_ERR);
        }
    }

    //logout User
    private void logoutUser() {

        if (user != null) {
            Server.connectedUsers.remove(user);
            System.out.println("Successfully logged out!");
            System.out.println("Connected User: " + Server.connectedUsers);

        }

    }

    private void deliverMsgs(User u) throws IOException, InterruptedException {
        Thread.sleep(500);
        List<Socket> s = new LinkedList<>();
        s.add(Server.connectedUsers.get(u));
        List<TextMessage> msgs = new LinkedList<>();
        for (TextMessage m:Server.undeliveredMsgs) {
            if(m.getChat().getUsers().contains(u)) {
                new SenderThread(m, getSockets(m.getChat())).start();
                Thread.sleep(500);
                System.err.println("msg sent to "+u + "/"+m+"/"+m.getChat()+"/"+m.getType()+"/"+Server.connectedUsers.get(u)+"/"+m.getSrc());
                msgs.add(m);
            }

        }

        System.out.println("---msgs---"+Server.undeliveredMsgs);

        for (TextMessage m : msgs) {
            Server.undeliveredMsgs.remove(m);
        }

        System.out.println("---msgs---"+Server.undeliveredMsgs);
    }

}
