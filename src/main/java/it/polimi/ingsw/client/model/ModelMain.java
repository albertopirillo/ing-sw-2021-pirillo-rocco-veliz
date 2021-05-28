package it.polimi.ingsw.client.model;

public class ModelMain {
    private final MarketModel marketModel;
    private final PersonalBoardModel personalBoardModel;
    private final SoloGameModel soloGameModel;
    private final StoragesModel storagesModel;

    public ModelMain(MarketModel marketModel, PersonalBoardModel personalBoardModel, SoloGameModel soloGameModel, StoragesModel storagesModel) {
        this.marketModel = marketModel;
        this.personalBoardModel = personalBoardModel;
        this.soloGameModel = soloGameModel;
        this.storagesModel = storagesModel;
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

    public StoragesModel getStoragesModel() {
        return storagesModel;
    }
}
