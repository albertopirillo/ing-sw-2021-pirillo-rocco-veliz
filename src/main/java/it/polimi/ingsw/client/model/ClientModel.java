package it.polimi.ingsw.client.model;

public class ClientModel {
    private final MarketModel marketModel;
    private final PersonalBoardModel personalBoardModel;
    private final SoloGameModel soloGameModel;
    private final StorageModel storageModel;

    public ClientModel() {
        this.marketModel = new MarketModel();
        this.personalBoardModel = new PersonalBoardModel();
        this.soloGameModel = new SoloGameModel();
        this.storageModel = new StorageModel();
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
