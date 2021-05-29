package it.polimi.ingsw.client.model;

public class ClientModel {
    private final MarketModel marketModel;
    private final PersonalBoardModel personalBoardModel;
    private final SoloGameModel soloGameModel;
    private final StorageModel storageModel;
    private String nickname;

    public ClientModel() {
        this.marketModel = new MarketModel(this);
        this.personalBoardModel = new PersonalBoardModel(this);
        this.soloGameModel = new SoloGameModel(this);
        this.storageModel = new StorageModel(this);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public MarketModel getMarketModel() {
        return marketModel;
    }

    public PersonalBoardModel getPersonalBoardModel() {
        return personalBoardModel;
    }

    public SoloGameModel getSoloGameModel() {
        return soloGameModel;
    }

    public StorageModel getStoragesModel() {
        return storageModel;
    }

}
