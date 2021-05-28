package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.DevelopmentCard;

import java.util.List;
import java.util.stream.Collectors;

public class MarketUpdate extends ServerUpdate {
    private final List<DevelopmentCard> devCardList;

    public MarketUpdate(String activePlayer, List<DevelopmentCard> devCardList) {
        super(activePlayer);
        this.devCardList = devCardList;
    }

    public List<DevelopmentCard> getDevCardList() {
        return devCardList;
    }

    public List<String> getCardImgs(){
        return devCardList.stream().map(e -> e.getImg()).collect(Collectors.toList());
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateMarket(this);
    }
}
