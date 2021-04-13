package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseResourceStrategyTest {

    @Test
    public void standardTest() throws FullCardDeckException {
        Game game = new Game(1, null);
        Player player = new Player(false, "abc", game, 0, 0);
    }

}