package org.academiadecodigo.agicultures;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMircMain{
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private static int port;
    private Scanner sc = new Scanner(System.in);

    public ServerMircMain()  {
        System.out.printf("Qual a porta a Abrir?:");
        port = sc.nextInt();
        try {
            serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
                clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                Thread thread = new Thread(clientHandler);
                thread.start();

        }
        } catch (IOException e) {
            close();
        }
    }
    public void close() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
            new ServerMircMain();
    }
}
