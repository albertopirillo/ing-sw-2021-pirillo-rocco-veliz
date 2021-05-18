package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class NicknameErrorMessage extends Message {
    private final String nickname;

    public NicknameErrorMessage(String activePlayer, String nickname) {
        super(activePlayer);
        this.nickname = nickname;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.changeNickname();
    }

    @Override
    public void process(Server server, Connection connection) {

    }
}
