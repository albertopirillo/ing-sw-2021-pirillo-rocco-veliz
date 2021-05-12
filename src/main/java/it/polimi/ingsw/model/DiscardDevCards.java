package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.ArrayList;
import java.util.List;

public class DiscardDevCards extends SoloActionToken {

    private final CardColor color;

    public DiscardDevCards(SoloGame game, CardColor color) {
        super(game);
        this.color = color;
    }

    @Override
    public void reveal() throws NegativeResAmountException, InvalidKeyException {
        System.out.println("[SOLO_ACTION_TOKEN] Discard cards: " + this.color.toString());
        SoloGame game = this.getGame();
        Market market = game.getMarket();
        List<DevelopmentCard> discardedCards = new ArrayList<>();
        int level = 1, discardCount = 0;
        while (discardCount != 2) {
            //No more cards in that column
            if (level > 3) {
                this.getGame().lastTurn(false);
            }
            try {
                DevelopmentCard card = market.buyCards(level, this.color);
                discardedCards.add(card);
                discardCount++;

                //No more cards in that column
                if (level == 3 && market.isDeckEmpty(level, this.color)){
                    this.getGame().lastTurn(false);
                }
            } catch (DeckEmptyException e) {
                //No more cards of that level
                level++;
            }
        }
        game.updateDiscardedCards(discardedCards);
    }

    @Override
    public String toString() {
        return "Discard 2 development cards of " + this.color + " color";
    }
}