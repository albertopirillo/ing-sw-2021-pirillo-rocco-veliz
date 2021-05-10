package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ShowStoragesRequest extends Request{
    private final String player;

    public ShowStoragesRequest(String player) {
        this.player = player;
    }

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().showStorages(this.player);
    }
}
