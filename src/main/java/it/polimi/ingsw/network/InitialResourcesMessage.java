package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public class InitialResourcesMessage extends ServerUpdate {

    private final int numPlayer;

    public InitialResourcesMessage(String activePlayer, boolean lastUpdate, int numPlayer) {
        super(activePlayer, lastUpdate);
        this.numPlayer = numPlayer;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(getActivePlayer().equals(playerInterface.getNickname())) {
            playerInterface.getInitialResources(numPlayer);
        }
    }
}
