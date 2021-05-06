package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.AbilityChoice;

public class InsertMarbleRequest extends Request {

    private final int position;
    private final AbilityChoice choice;
    private final int amount1;
    private final int amount2;

    public InsertMarbleRequest(int position, AbilityChoice choice, int amount1, int amount2) {
        super();
        this.position = position;
        this.choice = choice;
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().insertMarble(position, choice, amount1, amount2);
    }
}