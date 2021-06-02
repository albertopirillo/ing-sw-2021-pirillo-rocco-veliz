package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.ArrayList;
import java.util.List;

/**
 * Action Token implementation that discards two development cards of a given color from the Market
 */
public class DiscardDevCardsToken extends SoloActionToken {

    private final CardColor color;

    /**
     * Constructs a new Discard Action Token
     * @param game the associated game
     * @param color the color of the cards that will be discarded
     */
    public DiscardDevCardsToken(SoloGame game, CardColor color) {
        super(game);
        this.color = color;
    }

    /**
     * <p>Activates the effects of this action token</p>
     * <p>If, after the cards were discarded, there are no more cards in that column, the game is over</p>
     * <p>Same if there are no more cards of a generic level</p>
     * <p>In those cases, the player lost the game</p>
     */
    @Override
    public void reveal() throws NegativeResAmountException {
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
    public String getID() {
        return color.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "Discard 2 development cards of " + this.color + " color";
    }
}