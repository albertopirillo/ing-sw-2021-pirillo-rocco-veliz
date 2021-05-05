package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class LoginMessage extends Message {

    private String nickname;

    public LoginMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        //playerInterface.setNickname(nickname);
        playerInterface.getGameSize();
    }

    @Override
    public void process(Server server, Connection connection) {
        server.lobby(nickname, connection);
    }
}
