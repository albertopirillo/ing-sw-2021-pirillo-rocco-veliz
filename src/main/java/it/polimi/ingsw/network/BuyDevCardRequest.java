package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.CardColor;
import it.polimi.ingsw.model.Resource;

public class BuyDevCardRequest extends Request {

    public BuyDevCardRequest() {
    }

    private int level;

    private CardColor color;

    private AbilityChoice choice;

    private Resource fromDepot;

    private Resource fromStrongbox;

    private int devSlot;

    public void activateRequest(MasterController masterController) {
        // TODO implement here
    }
}