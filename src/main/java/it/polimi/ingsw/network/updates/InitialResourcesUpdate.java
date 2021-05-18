package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class InitialResourcesUpdate extends ServerUpdate {

    private final int numPlayer;

    public InitialResourcesUpdate(String activePlayer, int numPlayer) {
        super(activePlayer);
        this.numPlayer = numPlayer;
    }

    @Override
    public void update(UserInterface userInterface) {
        if (this.getActivePlayer().equals(userInterface.getNickname())) {
            userInterface.viewInitialResources(numPlayer);
        }
    }
}
