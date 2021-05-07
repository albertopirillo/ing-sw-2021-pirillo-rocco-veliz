package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.DevelopmentCard;

import java.util.List;

public class MarketUpdate extends ServerUpdate {
    private final List<DevelopmentCard> devCardList;

    public MarketUpdate(String activePlayer, List<DevelopmentCard> devCardList) {
        super(activePlayer);
        this.devCardList = devCardList;
    }

    public List<DevelopmentCard> getDevCardList() {
        return devCardList;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateMarket(this);
    }
}
