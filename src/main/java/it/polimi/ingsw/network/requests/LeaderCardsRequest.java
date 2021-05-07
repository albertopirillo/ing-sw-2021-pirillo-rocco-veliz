package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class LeaderCardsRequest extends Request{
    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().showLeaderCards();
    }
}
