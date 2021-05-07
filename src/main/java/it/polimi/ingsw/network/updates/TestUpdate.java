package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;

@Deprecated
public class TestUpdate extends ServerUpdate {

    private final String text;

    public TestUpdate(String activePlayer, String text) {
        super(activePlayer);
        this.text = text;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.simulateGame();
    }
}
