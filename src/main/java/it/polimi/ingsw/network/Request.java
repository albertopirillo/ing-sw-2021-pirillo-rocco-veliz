package it.polimi.ingsw.network;

public abstract class Request extends Message {

    public Request() {
    }

    public abstract void activateRequest();
}