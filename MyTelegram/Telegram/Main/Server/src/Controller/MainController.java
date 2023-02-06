package Controller;

import Models.*;
import Network.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class MainController {

    private final NetworkManager networkManager;
    private final ArrayList<RequestHandler> connectedClients;

    public MainController() throws IOException {
        this.networkManager = new NetworkManager(this);
        this.connectedClients = new ArrayList<>();
    }

    public void start() {
        while (true) {
            Socket socket = networkManager.waitForAClient();
            System.out.println("A client connected!");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestHandler requestHandler = new RequestHandler(MainController.this, socket);
                        connectedClients.add(requestHandler);
                        System.out.println("client added to list!");
                        requestHandler.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        MainController mainController = new MainController();
        mainController.start();
    }
}
