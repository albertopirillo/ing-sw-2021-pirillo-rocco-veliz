package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.Resource;

public class TempResourceUpdate extends ServerUpdate {
    private final Resource resource;

    public TempResourceUpdate(String activePlayer, Resource resource) {
        super(activePlayer);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public void update(UserInterface userInterface) {
        if (this.getActivePlayer().equals(userInterface.getNickname())) {
            userInterface.updateTempResource(this);
        }
    }
}
