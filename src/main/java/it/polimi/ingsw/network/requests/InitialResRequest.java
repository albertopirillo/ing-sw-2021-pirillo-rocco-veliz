package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.ResourceType;

import java.util.Map;

public class InitialResRequest extends Request {

    private final Map<ResourceType, Integer> res;
    private int numPlayer;
    private String player;

    public InitialResRequest(Map<ResourceType, Integer> res) {
        super();
        this.res = res;
    }

    public void setNumPlayer(int numPlayer){
        this.numPlayer = numPlayer;
    }

    public void setPlayer(String nickname){
        this.player = nickname;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getSetupController().placeInitialResource(res, numPlayer, player);
    }
}