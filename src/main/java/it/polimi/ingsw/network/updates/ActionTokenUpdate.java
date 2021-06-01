package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.SoloActionToken;

public class ActionTokenUpdate extends ServerUpdate {

    private final SoloActionToken lastToken;

    public ActionTokenUpdate(String activePlayer, SoloActionToken lastToken) {
        super(activePlayer);
        this.lastToken = lastToken;
    }

    public SoloActionToken getLastToken() {
        return lastToken;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateSoloTokens(this);
    }
}
