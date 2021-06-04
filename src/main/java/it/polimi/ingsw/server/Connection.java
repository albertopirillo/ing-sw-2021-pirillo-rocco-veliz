package it.polimi.ingsw.server;

import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.network.updates.ServerUpdate;


public abstract class Connection implements Runnable {

    private final Server server;
    private RemoteView remoteView;
    private boolean active;

    public Connection(Server server){
        this.server = server;
        this.active = true;
        System.out.println("[SERVER] Client connection established.");
    }

    public Server getServer() {return server;}
    public void setRemoteView(RemoteView remoteView){ this.remoteView = remoteView; }
    public RemoteView getRemoteView(){ return this.remoteView; }
    protected synchronized boolean isActive(){
        return active;
    }
    protected synchronized void setActive(boolean active) {this.active = active;}

    @Override
    public abstract void run();
    public abstract void sendMessage(ServerUpdate message);
    public abstract void close();
}