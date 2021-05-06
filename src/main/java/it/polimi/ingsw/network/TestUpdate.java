package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public class TestUpdate extends ServerUpdate {

    private final String text;

    public TestUpdate(String activePlayer, boolean lastUpdate, String text) {
        super(activePlayer, lastUpdate);
        this.text = text;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(getActivePlayer().equals(playerInterface.getNickname())) {
            playerInterface.simulateGame();
        }

    }
}