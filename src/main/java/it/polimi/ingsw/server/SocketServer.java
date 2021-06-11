package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.ErrorMessage;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.messages.NicknameErrorMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>Implementation of the Server that uses TCP to communicate with the Client</p>
 * <p>Port can be specified as a program argument</p>
 */
public class SocketServer extends Server{

    private final int port;
    private final ServerSocket serverSocket;

    /**
     * Creates a new instance of a Server
     * @param port the port of the Socket
     */
    public SocketServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.enableLogging(true);
    }

    @Override
    public void login(String nickname, Connection connection) {
        if(checkUsernameExist(nickname)){
            ServerUpdate msg = new NicknameErrorMessage(nickname, nickname);
            connection.sendMessage(msg);
            return;
        }
        if(!getLobbyPlayers().isEmpty() && getPlayerAmount()==0){
            ServerUpdate msg = new ErrorMessage(nickname, "A game is being created. Please wait for the host");
            connection.sendMessage(msg);
            return;
        }
        System.out.println("[SERVER] New player " + nickname + " added");
        if(getLobbyPlayers().isEmpty()){
            addToLobby(nickname, connection);
            ServerUpdate msg = new LoginMessage(nickname, nickname);
            connection.sendMessage(msg);
        } else {
            addToLobby(nickname, connection);
        }
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
