package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;

public class DiscardDevCards extends SoloActionToken {

    private final CardColor color;

    public DiscardDevCards(SoloGame game, CardColor color) {
        super(game);
        this.color = color;
    }

    @Override
    public void reveal() {
        Market market = this.getGame().getMarket();
        int level = 1, discardCount = 0;
        while (discardCount != 2) {
            //No more cards in that column
            if (level > 3)
                ;//TODO:endgame(LOST)
            try {
                market.buyCards(level, this.color);
                discardCount++;
                //No more cards in that column
                if (level == 3 && market.isDeckEmpty(level, this.color))
                    ;//TODO: endgame(LOST)
            } catch (DeckEmptyException e) {
                //No more cards of that level
                level++;
            }
        }
    }
}