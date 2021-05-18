package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class TempMarblesUpdate extends ServerUpdate{

    private final int numWhiteMarbles;

    private final List<ResourceType> resources;

    public TempMarblesUpdate(String activePlayer, int numWhiteMarbles, List<ResourceType> resources) {
        super(activePlayer);
        this.numWhiteMarbles = numWhiteMarbles;
        this.resources = resources;
    }

    public int getNumWhiteMarbles() {
        return numWhiteMarbles;
    }

    public List<ResourceType> getResources() {
        return resources;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateTempMarbles(this);
    }

    @Override
    public String toString() {
        return "You have "+ numWhiteMarbles + "  white marbles!";
    }
}
