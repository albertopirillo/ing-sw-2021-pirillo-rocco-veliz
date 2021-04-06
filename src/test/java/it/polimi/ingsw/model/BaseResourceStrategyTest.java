package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseResourceStrategyTest {

    @Test
    public void standardTest() {
        Game game = new Game();
        Player player = new Player(false, "abc", game);
    }

}