package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class EndTurnRequest extends Request {

    public EndTurnRequest() {
        super();
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().endTurn();
    }
}