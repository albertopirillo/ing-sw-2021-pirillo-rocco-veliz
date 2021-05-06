package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class LoginMessage extends Message {

    private final String nickname;

    public LoginMessage(String activePlayer, boolean lastUpdate, String nickname) {
        super(activePlayer, lastUpdate);
        this.nickname = nickname;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        //playerInterface.setNickname(nickname);
        playerInterface.getGameSize();
    }

    @Override
    public void process(Server server, Connection connection) {
        server.login(nickname, connection);
    }
}
