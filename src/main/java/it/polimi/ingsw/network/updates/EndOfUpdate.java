package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;

public class EndOfUpdate extends ServerUpdate {

    public EndOfUpdate(String activePlayer) {
        super(activePlayer);
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.endOfUpdate();
    }
}
