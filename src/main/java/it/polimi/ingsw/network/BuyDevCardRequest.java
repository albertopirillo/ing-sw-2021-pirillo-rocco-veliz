package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.CardColor;
import it.polimi.ingsw.model.Resource;

public class BuyDevCardRequest extends Request {

    private final int level;
    private final CardColor color;
    private final int devSlot;
    private final AbilityChoice choice;
    private final Resource fromDepot;
    private final Resource fromStrongbox;


    public BuyDevCardRequest(int level, CardColor color, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, int devSlot) {
        super();
        this.level = level;
        this.color = color;
        this.devSlot = devSlot;
        this.choice = choice;
        this.fromDepot = fromDepot;
        this.fromStrongbox = fromStrongbox;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().buyDevCard(level, color, devSlot, choice, fromDepot, fromStrongbox);
    }
}