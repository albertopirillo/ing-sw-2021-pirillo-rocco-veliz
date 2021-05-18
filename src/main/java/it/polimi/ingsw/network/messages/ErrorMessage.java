package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class ErrorMessage extends Message {
    private final String text;

    public ErrorMessage(String activePlayer, String text) {
        super(activePlayer);
        this.text = text;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.errorPrint(text);
        playerInterface.loginMessage();
    }

    @Override
    public void process(Server server, Connection connection) {

    }
}
