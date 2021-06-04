package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Server{

    private final int port;
    private final ServerSocket serverSocket;

    public SocketServer() throws IOException {
        this.port = 8080;
        this.serverSocket = new ServerSocket(8080);
    }

    public SocketServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("[SERVER] Server is listening on port: " + port);
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] New client connected");
                newConnection(socket);
            } catch (IOException e) {
                System.out.println("Connection Error");
                e.printStackTrace();
            }
        }
    }

    private void newConnection(Socket socket) {
        Connection connection = new SocketConnection(socket, this);
        registerConnection(connection);
        new Thread(connection).start();
    }
}
