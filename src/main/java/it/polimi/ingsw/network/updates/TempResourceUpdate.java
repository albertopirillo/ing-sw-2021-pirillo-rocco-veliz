package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Resource;

public class TempResourceUpdate extends ServerUpdate {
    private final Resource resource;
    private final int numWhiteMarbles = 0;

    public TempResourceUpdate(String activePlayer, Resource resource) {
        super(activePlayer);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateTempResource(this);
    }
}
