package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

/**
 * Notifies the client that the player has performed a market action
 */
public class MarketActionDoneUpdate extends ServerUpdate{

    public MarketActionDoneUpdate(String activePlayer) {
        super(activePlayer);
    }


    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateMarketActionDone(this);
    }
}

