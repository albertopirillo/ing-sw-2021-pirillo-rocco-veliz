package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.ClientError;

public class ErrorUpdate extends ServerUpdate {

    private final ClientError clientError;

    public ErrorUpdate(String activePlayer, boolean lastUpdate, ClientError clientError) {
        super(activePlayer, lastUpdate);
        this.clientError = clientError;
    }

    public ClientError getClientError() {
        return clientError;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.displayError(this);
    }

    @Override
    public String toString() {
        return clientError.getError();
    }
}
