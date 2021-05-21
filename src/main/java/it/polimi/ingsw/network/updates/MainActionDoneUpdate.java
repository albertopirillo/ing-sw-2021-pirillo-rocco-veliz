package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class MainActionDoneUpdate extends ServerUpdate {

    public MainActionDoneUpdate(String activePlayer) {
        super(activePlayer);
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateActionDone(this);
    }
}
