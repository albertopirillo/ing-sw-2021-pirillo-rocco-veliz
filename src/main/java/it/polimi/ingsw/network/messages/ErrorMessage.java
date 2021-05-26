package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class ErrorMessage extends Message {
    private final String text;

    public ErrorMessage(String activePlayer, String text) {
        super(activePlayer);
        this.text = text;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.waitForHostError(text);
        userInterface.loginMessage();
    }

    @Override
    public void process(Server server, Connection connection) {

    }
}
