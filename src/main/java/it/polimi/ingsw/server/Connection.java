package it.polimi.ingsw.server;

import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.network.updates.ServerUpdate;

/**
 * <p>Generic abstract implementation of a Connection between Client and Server</p>
 * <p>Uses the RemoteView to build new Updates</p>
 * <p>Can send messages in the two directions</p>
 */
public abstract class Connection implements Runnable {

    private final Server server;
    private RemoteView remoteView;
    private boolean active;

    /**
     * Creates a new instance of a connection
     * @param server the server that will be part of the connection
     */
    public Connection(Server server){
        this.server = server;
        this.active = true;
    }

    /**
     * Keeps the connection listening for Requests from the Client
     */
    @Override
    public abstract void run();

    /**
     * Sends a message using this connection
     * @param message the message to send
     */
    public abstract void sendMessage(ServerUpdate message);

    /**
     * Closes this connection, stopping the thread
     */
    public abstract void close();

    /**
     * Gets the Server that is part of this connection
     * @return the Server
     */
    public Server getServer() {return server;}

    /**
     * Sets the RemoteView associated with this connection
     * @param remoteView the RemoteView to set
     */
    public void setRemoteView(RemoteView remoteView){ this.remoteView = remoteView; }

    /**
     * Gets the RemoteView associated with this connection
     * @return the RemoteView associated
     */
    public RemoteView getRemoteView(){ return this.remoteView; }

    /**
     * Whether the connection is already active or not
     * @return true if it is active, false otherwise
     */
    protected synchronized boolean isActive(){
        return active;
    }

    /**
     * Sets the active flag to false
     */
    protected synchronized void setInactive() {this.active = false;}
}