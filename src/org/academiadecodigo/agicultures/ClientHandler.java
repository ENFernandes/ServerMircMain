package org.academiadecodigo.agicultures;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private static ArrayList<ClientHandler> clientHandlerList = new ArrayList<>();
    private BufferedWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    private String name;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.name = in.readLine();
        clientHandlerList.add(this);
    }

    @Override
    public void run() {
        String msg;
        while (clientSocket.isConnected()) {
            try {
                msg = in.readLine();
                System.out.println(msg);
            } catch (IOException e) {
                removeClient();
                close();
                break;
            }
        }
    }
    public void removeClient(){
        clientHandlerList.remove(this);
    }
    public void close() {
        removeClient();
        try {
            if(clientSocket != null) {
                clientSocket.close();
            }
            if(in!=null) {
                in.close();
            }
            if (out!=null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
