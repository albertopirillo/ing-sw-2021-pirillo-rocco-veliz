package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class NicknameErrorMessage extends Message {

    public NicknameErrorMessage(String activePlayer, String nickname) {
        super(activePlayer);
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.changeNickname();
    }

    @Override
    public void process(Server server, Connection connection) {

    }
}
