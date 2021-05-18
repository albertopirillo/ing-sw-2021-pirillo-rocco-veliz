package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class EndOfUpdate extends ServerUpdate {

    public EndOfUpdate(String activePlayer) {
        super(activePlayer);
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.endOfUpdate(this);
    }
}
