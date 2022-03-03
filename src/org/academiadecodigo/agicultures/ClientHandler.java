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
        msgClient("Server:" + name + "esta ai!");
    }
    public void msgClient(String msgToSee) {
        for (ClientHandler c : clientHandlerList) {
            try {
                if (!c.name.equals(name)) {
                    c.out.write(msgToSee);
                    c.out.newLine();
                    c.out.flush();
                }
            } catch (IOException e) {
                close();
            }
        }
    }
    @Override
    public void run() {
        String msg;
        while (true) {
            try {
                msg = in.readLine();
                msgClient(msg);
            } catch (IOException e) {
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
