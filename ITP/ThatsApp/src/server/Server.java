package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Server Klasse
 */


public class Server {

    public static void main(String[] args) {
         ServerSocket serverSocket;



        final Scanner sc = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                PrintWriter out;
                BufferedReader in;
                Socket clientSocket;
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Thread sender = new Thread(new Runnable() {
                    String msg;

                    @Override
                    public void run() {
                        while (true) {
                            msg = sc.nextLine(); //reads data from user's keybord
                            out.println(msg);    // write data stored in msg in the clientSocket
                            out.flush();   // forces the sending of the data
                        }
                    }


                });
                sender.start();


                Thread receive = new Thread(new Runnable() {
                    String msg;

                    @Override
                    public void run() {
                        try {
                            msg = in.readLine();

                            while (msg != null) {
                                System.out.println("Client: "+ Thread.currentThread().getId()  + msg);
                                msg = in.readLine();
                            }

                            System.out.println("Client disconnected");

                            out.close();
                            clientSocket.close();
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


                receive.start();
            }
        } catch(IOException e){
                e.printStackTrace();
            }

    }


}


