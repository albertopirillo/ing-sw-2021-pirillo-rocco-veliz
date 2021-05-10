package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class QuitGameRequest extends Request{
    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().quitGame();
    }
}
