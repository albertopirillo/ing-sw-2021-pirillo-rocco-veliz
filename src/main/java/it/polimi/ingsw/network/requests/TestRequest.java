package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class TestRequest extends Request{
    @Override
    public void activateRequest(MasterController masterController) {
        masterController.simulateGame();
    }
}
