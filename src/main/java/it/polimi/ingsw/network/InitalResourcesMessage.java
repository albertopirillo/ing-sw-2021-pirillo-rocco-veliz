package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public class InitalResourcesMessage extends ServerUpdate {

    private int numPlayer;

    public InitalResourcesMessage(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(getActivePlayer().equals(playerInterface.getNickname())) {
            playerInterface.getInitialResources(numPlayer);
        }
    }
}
