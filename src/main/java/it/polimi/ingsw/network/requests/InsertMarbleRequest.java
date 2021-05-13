package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class InsertMarbleRequest extends Request {

    private final int position;

    public InsertMarbleRequest(int position/*, AbilityChoice choice, int amount1, int amount2*/) {
        super();
        this.position = position;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().insertMarble(position);
    }
}