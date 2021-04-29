package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

public class TestRequest extends Request{
    @Override
    public void activateRequest(MasterController masterController) {
        masterController.simulateGame();
    }
}
