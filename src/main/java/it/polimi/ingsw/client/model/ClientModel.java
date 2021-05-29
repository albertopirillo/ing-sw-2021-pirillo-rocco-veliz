package it.polimi.ingsw.client.model;

public class ClientModel {
    private final MarketModel marketModel;
    private final PersonalBoardModel personalBoardModel;
    private final SoloGameModel soloGameModel;
    private final StorageModel storageModel;

    public ClientModel(MarketModel marketModel, PersonalBoardModel personalBoardModel, SoloGameModel soloGameModel, StorageModel storageModel) {
        this.marketModel = marketModel;
        this.personalBoardModel = personalBoardModel;
        this.soloGameModel = soloGameModel;
        this.storageModel = storageModel;
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
