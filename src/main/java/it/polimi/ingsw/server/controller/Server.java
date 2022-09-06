package it.polimi.ingsw.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is server app
 */
public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Server echoServer = new Server(12345);
        echoServer.startServer();
    }

    /**
     * This is method is called to accept requests from clients
     * Each client will be managed with a Thread
     */
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Server ready on port: " + port);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ManageClient(socket));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();

        System.out.println("Server error");
    }
}
