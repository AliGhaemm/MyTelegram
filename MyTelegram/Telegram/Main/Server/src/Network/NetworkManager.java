package Network;

import Controller.*;

import java.io.*;
import java.net.*;

public class NetworkManager {

    private final ServerSocket serverSocket;
    private final MainController mainController;

    public NetworkManager (MainController mainController) throws IOException {
        this.mainController = mainController;
        this.serverSocket = new ServerSocket(11111);
    }

    public Socket waitForAClient () {
        try{
            return serverSocket.accept();
        } catch (IOException e) {
            return null;
        }
    }

}
