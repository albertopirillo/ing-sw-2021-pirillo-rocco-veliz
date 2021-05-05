package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public class TestUpdate extends ServerUpdate {

    private String text;

    public TestUpdate(String text) {
        this.text = text;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(getActivePlayer().equals(playerInterface.getNickname())) {
            playerInterface.simulateGame();
        }

    }
}
