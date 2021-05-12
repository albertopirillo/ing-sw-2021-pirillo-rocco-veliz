package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ChangeMarblesRequest extends Request {

    private final int amount1;
    private final int amount2;

    public ChangeMarblesRequest(int amount1, int amount2) {
        super();
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public int getAmount1() {
        return amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().changeWhiteMarbles(this);
    }
}
