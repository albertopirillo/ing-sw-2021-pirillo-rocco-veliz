package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.RequestController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.utils.ModelObserver;

import java.util.*;

public class RemoteView implements ModelObserver {
    private RequestController requestController;
    private final Connection connection;
    private final String player;

    public RemoteView(Connection connection, String player){
        this.connection = connection;
        this.player = player;
    }

    public void processRequest(Request request){
        System.out.println("[REMOTE VIEW] from player " + player);
        this.requestController.processRequest(request);
    }

    public RequestController getRequestController() {
        return requestController;
    }

    public void addController(RequestController requestController){ this.requestController = requestController; }

    @Override
    public void quitGame() {
        connection.close();
    }

    @Override
    public String getPlayer() {
        return this.player;
    }

    @Override
    public void notifyInitResources(Game game, int numPlayer){
        ServerUpdate msg = new InitialResourcesUpdate(game.getActivePlayer().getNickname(), numPlayer);
        connection.sendMessage(msg);
    }

    @Override
    public void showInitLeaderCards(Game game){
        //Prepare message with initial Leader Cards
        Player activePlayer = game.getActivePlayer();
        List<LeaderCard> cards = new ArrayList<>(activePlayer.getLeaderCards());
        ServerUpdate msg = new InitialLeaderUpdate(activePlayer.getNickname(), cards);
        connection.sendMessage(msg);
    }

    @Override
    public void showFaithTrack(Game game){
        ServerUpdate msg = buildFaithTrackUpdate(game);
        connection.sendMessage(msg);
    }

    @Override
    public void showLeaderCards(Game game){
       ServerUpdate msg = buildLeaderUpdate(game);
       connection.sendMessage(msg);
    }

    @Override
    public void gameStateChange(Game game){
        //Simulation of endTurnRequest and endUpdate
        //System.out.println("[REMOTE VIEW] Game in progress, turn of " + player);
        ServerUpdate msg = new EndOfUpdate(game.getActivePlayer().getNickname());
        connection.sendMessage(msg);
    }

    @Override
    public void notifyGameOver(Game game, boolean win, List<String> ranking, Map<String, Integer> scores) {
        String nickname = game.getActivePlayer().getNickname();
        ServerUpdate msg = new GameOverUpdate(nickname, win, new ArrayList<>(ranking), new HashMap<>(scores));
        connection.sendMessage(msg);
    }

    @Override
    public void showClientError(Game game, ClientError clientError) {
        String nickname = game.getActivePlayer().getNickname();
        try {
            ServerUpdate msg = new ErrorUpdate(nickname, clientError.clone());
            connection.sendMessage(msg);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMarketTray(Game game){
        ServerUpdate msg = buildMarketTrayUpdate(game);
        connection.sendMessage(msg);
    }

    @Override
    public void showMarket(Game game){
        ServerUpdate msg = buildMarketUpdate(game);
        connection.sendMessage(msg);
    }

    @Override
    public void showDevSlots(Game game) {
        ServerUpdate msg = buildDevSlotsUpdate(game);
        connection.sendMessage(msg);
    }

    @Override
    public void showStorages(Game game) {
        ServerUpdate msg = buildStorageUpdate(game);
        connection.sendMessage(msg);
    }

    @Override
    public void showTempRes(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        Resource tempRes = getRequestController().getMasterController().getResourceController().getTempRes().getToHandle();
        ServerUpdate msg = new TempResourceUpdate(nickname, new Resource(tempRes.getMap()));
        connection.sendMessage(msg);
    }

    @Override
    public void showTempMarbles(Game game, int numWhiteMarbles) {
        Player activePlayer = game.getActivePlayer();
        String nickname = activePlayer.getNickname();
        ServerUpdate msg = new TempMarblesUpdate(nickname, numWhiteMarbles, new ArrayList<>(activePlayer.getResTypesAbility()));
        connection.sendMessage(msg);
    }

    @Override
    public void showDiscardedCards(SoloGame soloGame, List<DevelopmentCard> cardList) {
        String nickname = soloGame.getActivePlayer().getNickname();
        ServerUpdate msg = new DiscardedCardsUpdate(nickname, new ArrayList<>(cardList));
        connection.sendMessage(msg);
    }

    @Override
    public void showLastActionToken(SoloGame soloGame, SoloActionToken lastToken) {
        String nickname = soloGame.getActivePlayer().getNickname();
        try {
            ServerUpdate msg = new ActionTokenUpdate(nickname, lastToken.clone());
            connection.sendMessage(msg);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProductionDone(Game game){
        String nickname = game.getActivePlayer().getNickname();
        ServerUpdate msg = new ProductionDoneUpdate(nickname);
        connection.sendMessage(msg);
    }

    @Override
    public void setMainActionDone(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        ServerUpdate msg = new MainActionDoneUpdate(nickname);
        connection.sendMessage(msg);
    }

    @Override
    public void gameStateStart(Game game) {
        //Build a EndOfInitialUpdate and send it
        String nickname = game.getActivePlayer().getNickname();
        StorageUpdate storageUpdate = buildStorageUpdate(game);
        LeaderUpdate leaderUpdate = buildLeaderUpdate(game);
        MarketTrayUpdate marketTrayUpdate = buildMarketTrayUpdate(game);
        MarketUpdate marketUpdate = buildMarketUpdate(game);
        FaithTrackUpdate faithTrackUpdate = buildFaithTrackUpdate(game);
        DevSlotsUpdate devSlotsUpdate = buildDevSlotsUpdate(game);
        ServerUpdate msg = new EndOfInitialUpdate(nickname, storageUpdate, leaderUpdate, marketTrayUpdate, marketUpdate, faithTrackUpdate, devSlotsUpdate);
        connection.sendMessage(msg);
    }

    private DevSlotsUpdate buildDevSlotsUpdate(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        Map<String, List<DevelopmentSlot>> map = new HashMap<>();
        for(Player p: game.getPlayersList()) {
            map.put(p.getNickname(), Arrays.asList(p.getPersonalBoard().getDevSlots()));
        }
        return new DevSlotsUpdate(nickname, new HashMap<>(map));
    }

    private FaithTrackUpdate buildFaithTrackUpdate(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        Map<String, FaithTrack> faithTrackInfoMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        try {
            for (Player player : players) {
                faithTrackInfoMap.put(player.getNickname(), player.getPersonalBoard().getFaithTrack().clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new FaithTrackUpdate(nickname, faithTrackInfoMap);
    }

    private StorageUpdate buildStorageUpdate(Game game) {
        Map<String, List<DepotSetting>> depotMap = new HashMap<>();
        Map<String, Resource> strongboxMap = new HashMap<>();
        for(Player p: game.getPlayersList()) {
            depotMap.put(p.getNickname(),p.getPersonalBoard().getDepot().toDepotSetting());
            Strongbox strongbox = p.getPersonalBoard().getStrongbox();
            strongboxMap.put(p.getNickname(), (strongbox.queryAllRes()).sum(strongbox.queryAllTempRes()));
        }
        return new StorageUpdate(game.getActivePlayer().getNickname(), new HashMap<>(depotMap), new HashMap<>(strongboxMap));
    }

    private LeaderUpdate buildLeaderUpdate(Game game) {
        Map<String, List<LeaderCard>> leaderCardsMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            leaderCardsMap.put(player.getNickname(), player.getLeaderCards());
        }
        return new LeaderUpdate(game.getActivePlayer().getNickname(), new HashMap<>(leaderCardsMap));
    }

    private MarketTrayUpdate buildMarketTrayUpdate(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        MarketTray marketTray = game.getMarket().getMarketTray();
        MarblesColor[][] tray = new MarblesColor[3][4];
        System.arraycopy(marketTray.getMarketMarbles(), 0, tray, 0, marketTray.getMarketMarbles().length);
        return new MarketTrayUpdate(nickname, tray, marketTray.getRemainingMarble());
    }

    private MarketUpdate buildMarketUpdate(Game game) {
        String nickname = game.getActivePlayer().getNickname();
        Market market = game.getMarket();
        return new MarketUpdate(nickname, new ArrayList<>(market.getAvailableCards()));
    }

}
