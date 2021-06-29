package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.RequestController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.utils.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteView implements ModelObserver {
    private RequestController requestController;
    private final Connection connection;
    private final String player;
    private boolean enableLog;

    public RemoteView(Connection connection, String player){
        this.connection = connection;
        this.player = player;
        this.enableLog = false;
    }

    public void enableLogging(boolean enable) {
        this.enableLog = enable;
    }

    public void processRequest(Request request){
        if (enableLog) {
            System.out.println("[REMOTE VIEW] from player " + player);
        }
        this.requestController.processRequest(request);
    }

    public RequestController getRequestController() {
        return requestController;
    }

    public void addController(RequestController requestController){ this.requestController = requestController; }

    private String getActivePlayer(){
        return this.requestController.getMasterController().getGame().getActivePlayer().getNickname();
    }

    @Override
    public void quitGame() {
        connection.close();
    }

    @Override
    public String getPlayer() {
        return this.player;
    }

    @Override
    public void notifyInitResources(int numPlayer){
        ServerUpdate msg = new InitialResourcesUpdate(getActivePlayer(), numPlayer);
        connection.sendMessage(msg);
    }

    @Override
    public void showInitLeaderCards(List<LeaderCard> leaderCards){
        //Prepare message with initial Leader Cards
        List<LeaderCard> clonedList = new ArrayList<>();
        for(LeaderCard card: leaderCards) {
            clonedList.add(card.clone());
        }
        ServerUpdate msg = new InitialLeaderUpdate( getActivePlayer(), clonedList);
        connection.sendMessage(msg);
    }

    @Override
    public void showFaithTrack(List<Player> players){
        ServerUpdate msg = buildFaithTrackUpdate(players);
        connection.sendMessage(msg);
    }

    @Override
    public void showLeaderCards(List<Player> players){
       ServerUpdate msg = buildLeaderUpdate(players);
       connection.sendMessage(msg);
    }

    @Override
    public void gameStateChange(){
        //Simulation of endTurnRequest and endUpdate
        //System.out.println("[REMOTE VIEW] Game in progress, turn of " + player);
        ServerUpdate msg = new EndOfUpdate(getActivePlayer());
        connection.sendMessage(msg);
    }

    @Override
    public void notifyGameOver(boolean win, List<String> ranking, Map<String, Integer> scores) {
        ServerUpdate msg = new GameOverUpdate(getActivePlayer(), win, ranking, scores);
        connection.sendMessage(msg);
    }

    @Override
    public void showClientError(ClientError clientError) {
        ServerUpdate msg = new ErrorUpdate(getActivePlayer(), clientError.clone());
        connection.sendMessage(msg);
    }

    @Override
    public void showMarketTray(MarketTray marketTray){
        ServerUpdate msg = buildMarketTrayUpdate(marketTray);
        connection.sendMessage(msg);
    }

    @Override
    public void showMarket(List<DevelopmentCard> cards){
        ServerUpdate msg = buildMarketUpdate(cards);
        connection.sendMessage(msg);
    }

    @Override
    public void showDevSlots(List<Player> players) {
        ServerUpdate msg = buildDevSlotsUpdate(players);
        connection.sendMessage(msg);
    }

    @Override
    public void showStorages(List<Player> players) {
        ServerUpdate msg = buildStorageUpdate(players);
        connection.sendMessage(msg);
    }

    @Override
    public void showTempRes() {
        Resource tempRes = getRequestController().getMasterController().getResourceController().getTempRes().getToHandle();
        ServerUpdate msg = new TempResourceUpdate(getActivePlayer(), tempRes.clone());
        connection.sendMessage(msg);
    }

    @Override
    public void showTempMarbles(List<ResourceType> resTypesAbility, int numWhiteMarbles) {
        ServerUpdate msg = new TempMarblesUpdate(getActivePlayer(), numWhiteMarbles, new ArrayList<>(resTypesAbility));
        connection.sendMessage(msg);
    }

    @Override
    public void showDiscardedCards(List<DevelopmentCard> cardList) {
        List<DevelopmentCard> clonedList = new ArrayList<>();
        for(DevelopmentCard card: cardList) {
            clonedList.add(card.clone());
        }
        ServerUpdate msg = new DiscardedCardsUpdate(getActivePlayer(), clonedList);
        connection.sendMessage(msg);
    }

    @Override
    public void showLastActionToken(SoloActionToken lastToken) {
        ServerUpdate msg = new ActionTokenUpdate(getActivePlayer(), lastToken.clone());
        connection.sendMessage(msg);
    }

    @Override
    public void setProductionDone(){
        ServerUpdate msg = new ProductionDoneUpdate(getActivePlayer());
        connection.sendMessage(msg);
    }

    @Override
    public void setSecondProductionDone(){
        ServerUpdate msg = new SecondProductionDoneUpdate(getActivePlayer());
        connection.sendMessage(msg);
    }

    @Override
    public void setMainActionDone() {
        ServerUpdate msg = new MainActionDoneUpdate(getActivePlayer());
        connection.sendMessage(msg);
    }

    @Override
    public void gameStateStart(Game game) {
        //Build a EndOfInitialUpdate and send it
        List<Player> players = game.getPlayersList();
        StorageUpdate storageUpdate = buildStorageUpdate(players);
        LeaderUpdate leaderUpdate = buildLeaderUpdate(players);
        MarketTrayUpdate marketTrayUpdate = buildMarketTrayUpdate(game.getMarket().getMarketTray());
        MarketUpdate marketUpdate = buildMarketUpdate(game.getMarket().getAvailableCards());
        FaithTrackUpdate faithTrackUpdate = buildFaithTrackUpdate(players);
        DevSlotsUpdate devSlotsUpdate = buildDevSlotsUpdate(players);
        ServerUpdate msg = new EndOfInitialUpdate(getActivePlayer(), storageUpdate, leaderUpdate, marketTrayUpdate, marketUpdate, faithTrackUpdate, devSlotsUpdate);
        connection.sendMessage(msg);
    }

    private DevSlotsUpdate buildDevSlotsUpdate(List<Player> players) {
        Map<String, List<DevelopmentSlot>> map = new HashMap<>();
        for(Player p: players) {
            DevelopmentSlot[] devSlots = p.getPersonalBoard().getDevSlots();
            List<DevelopmentSlot> clonedList = new ArrayList<>();
            for(DevelopmentSlot slot: devSlots) {
                clonedList.add(slot.clone());
            }
            map.put(p.getNickname(), clonedList);
        }
        return new DevSlotsUpdate(getActivePlayer(), map);
    }

    private FaithTrackUpdate buildFaithTrackUpdate(List<Player> players) {
        Map<String, FaithTrack> map = new HashMap<>();
        for (Player player : players) {
            map.put(player.getNickname(), player.getPersonalBoard().getFaithTrack().clone());
        }
        return new FaithTrackUpdate(getActivePlayer(), map);
    }

    private StorageUpdate buildStorageUpdate(List<Player> players) {
        Map<String, List<DepotSetting>> depotMap = new HashMap<>();
        Map<String, Resource> strongboxMap = new HashMap<>();
        for(Player p: players) {
            depotMap.put(p.getNickname(),p.getPersonalBoard().getDepot().toDepotSetting());
            Strongbox strongbox = p.getPersonalBoard().getStrongbox();
            Resource strongboxContent = (strongbox.queryAllRes()).sum(strongbox.queryAllTempRes());
            strongboxMap.put(p.getNickname(), strongboxContent.clone());
        }
        return new StorageUpdate(getActivePlayer(), depotMap, strongboxMap);
    }

    private LeaderUpdate buildLeaderUpdate(List<Player> players) {
        Map<String, List<LeaderCard>> leaderCardsMap = new HashMap<>();
        for(Player player: players){
            List<LeaderCard> clonedList = new ArrayList<>();
            for(LeaderCard card: player.getLeaderCards()) {
                clonedList.add(card.clone());
            }
            leaderCardsMap.put(player.getNickname(), clonedList);
        }
        return new LeaderUpdate(getActivePlayer(), leaderCardsMap);
    }

    private MarketTrayUpdate buildMarketTrayUpdate(MarketTray marketTray) {
        MarblesColor[][] tray = new MarblesColor[3][4];
        System.arraycopy(marketTray.getMarketMarbles(), 0, tray, 0, marketTray.getMarketMarbles().length);
        return new MarketTrayUpdate(getActivePlayer(), tray, marketTray.getRemainingMarble());
    }

    private MarketUpdate buildMarketUpdate(List<DevelopmentCard> cards) {
        List<DevelopmentCard> clonedList = new ArrayList<>();
        for(DevelopmentCard card: cards) {
            if(card == null) clonedList.add(null);
            else clonedList.add(card.clone());
        }
         return new MarketUpdate(getActivePlayer(), clonedList);
    }
}
