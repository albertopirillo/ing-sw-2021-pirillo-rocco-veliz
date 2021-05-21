package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

/**
 * Notifies the client that the player has performed a basic production and now
 * extra productions and development productions are playable
 */
public class ProductionDoneUpdate extends ServerUpdate{

    public ProductionDoneUpdate(String activePlayer) {
        super(activePlayer);
    }


    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateProductionDone(this);
    }
}
