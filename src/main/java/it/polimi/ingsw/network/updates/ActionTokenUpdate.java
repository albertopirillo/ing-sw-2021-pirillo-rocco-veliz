package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.SoloActionToken;

public class ActionTokenUpdate extends ServerUpdate {

    private final SoloActionToken nextToken;

    public ActionTokenUpdate(String activePlayer, SoloActionToken nextToken) {
        super(activePlayer);
        this.nextToken = nextToken;
    }

    public SoloActionToken getNextToken() {
        return nextToken;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateSoloTokens(this);
    }
}
