package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.MarblesColor;

public class MarketTrayUpdate extends ServerUpdate {
    private final MarblesColor[][] marketTray;
    private final MarblesColor remainingMarble;

    public MarketTrayUpdate(String activePlayer, MarblesColor[][] marketTray, MarblesColor remainingMarble) {
        super(activePlayer);
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }

    public MarblesColor[][] getMarketTray() {
        return marketTray;
    }

    public MarblesColor getRemainingMarble() {
        return remainingMarble;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateMarketTray(this);
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        int index = 6;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix.append(marketTray[i][j]).append(" \t");
            }
            matrix.append("(").append(index).append(")").append("\n");
            index--;
        }
        matrix.append(" (0)\t (1)\t (2)\t (3)\n");
        matrix.append("Remaining marble = ").append(remainingMarble);
        return matrix.toString();
    }
}
