package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Player;

public class PlayerUpdate extends ServerUpdate {
    private final Player player;

    public PlayerUpdate(String activePlayer, Player player) {
        super(activePlayer);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updatePlayer(this);
    }
}