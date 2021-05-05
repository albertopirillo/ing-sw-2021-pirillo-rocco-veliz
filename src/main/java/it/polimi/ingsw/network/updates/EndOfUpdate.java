package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;

public class EndOfUpdate extends ServerUpdate {

    public EndOfUpdate(String activePlayer, boolean lastUpdate) {
        super(activePlayer, lastUpdate);
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.endOfUpdate();
    }
}
