package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class LoginMessage extends Message {

    private final String nickname;

    public LoginMessage(String activePlayer, String nickname) {
        super(activePlayer);
        this.nickname = nickname;
    }

    @Override
    public void update(UserInterface userInterface) {
        //playerInterface.setNickname(nickname);
        userInterface.getGameSize();
    }

    @Override
    public void process(Server server, Connection connection) {
        server.login(nickname, connection);
    }
}
