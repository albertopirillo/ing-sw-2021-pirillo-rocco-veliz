package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ShowLeaderCardsRequest extends Request{

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateLeaderCards();
        masterController.getGame().notifyEndOfUpdates();
    }
}
