package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;

public class InitialResourcesUpdate extends ServerUpdate {

    private final int numPlayer;

    public InitialResourcesUpdate(String activePlayer, boolean lastUpdate, int numPlayer) {
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
