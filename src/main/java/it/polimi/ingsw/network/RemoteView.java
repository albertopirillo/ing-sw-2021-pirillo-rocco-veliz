package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.utils.ModelObserver;

import java.util.*;

public class RemoteView implements ModelObserver {
    private MasterController masterController;
    private final Connection connection;
    private final String player;

    public RemoteView(Connection connection, String player){
        this.connection = connection;
        this.player = player;
    }

    public void processRequest(Request request){
        System.out.println("[REMOTE VIEW] from player " + player);
        this.masterController.processRequest(request);
    }

    public MasterController getMasterController() {
        return masterController;
    }

    public void addController(MasterController masterController){ this.masterController = masterController; }

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
        Map<String, FaithTrack> faithTrackInfoMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            faithTrackInfoMap.put(player.getNickname(), player.getPersonalBoard().getFaithTrack());
        }
        FaithTrackUpdate faithTrackMsg = new FaithTrackUpdate(game.getActivePlayer().getNickname(), faithTrackInfoMap);
        connection.sendMessage(faithTrackMsg);
    }

    @Override
    public void showLeaderCards(Game game){
        Map<String, List<LeaderCard>> leaderCardsMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            leaderCardsMap.put(player.getNickname(), player.getLeaderCards());
        }
        LeaderUpdate leaderUpdateMsg = new LeaderUpdate(game.getActivePlayer().getNickname(), leaderCardsMap);
        connection.sendMessage(leaderUpdateMsg);
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
        ServerUpdate msg = new GameOverUpdate(game.getActivePlayer().getNickname(), win, ranking, scores);
        connection.sendMessage(msg);
    }

    @Override
    public void showClientError(Game game, ClientError clientError) {
        Player activePlayer = game.getActivePlayer();
        ServerUpdate msg = new ErrorUpdate(activePlayer.getNickname(), clientError);
        connection.sendMessage(msg);
    }

    @Override
    public void showMarketTray(Game game){
        Player activePlayer = game.getActivePlayer();
        MarketTray marketTray = game.getMarket().getMarketTray();
        ServerUpdate msg = new MarketTrayUpdate(activePlayer.getNickname(), marketTray.getMarketMarbles(), marketTray.getRemainingMarble());
        connection.sendMessage(msg);
    }

    @Override
    public void showMarket(Game game){
        Player activePlayer = game.getActivePlayer();
        Market market = game.getMarket();
        ServerUpdate msg = new MarketUpdate(activePlayer.getNickname(), market.getAvailableCards());
        connection.sendMessage(msg);
    }

    @Override
    public void showDevSlots(Game game) {
        Player activePlayer = game.getActivePlayer();
        Map<String, List<DevelopmentSlot>> map = new HashMap<>();
        for(Player p: game.getPlayersList()) {
            map.put(p.getNickname(), Arrays.asList(p.getPersonalBoard().getDevSlots()));
        }
        ServerUpdate msg = new DevSlotsUpdate(activePlayer.getNickname(), map);
        connection.sendMessage(msg);
    }


    @Override
    public void showStorages(Game game) {
        Player activePlayer = game.getActivePlayer();
        Map<String, List<DepotSetting>> depotMap = new HashMap<>();
        Map<String, Resource> strongboxMap = new HashMap<>();
        for(Player p: game.getPlayersList()) {
            depotMap.put(p.getNickname(),p.getPersonalBoard().getDepot().toDepotSetting());
            strongboxMap.put(p.getNickname(), p.getPersonalBoard().getStrongbox().queryAllRes());
        }
        ServerUpdate msg = new StorageUpdate(activePlayer.getNickname(), depotMap, strongboxMap);
        connection.sendMessage(msg);
    }

    @Override
    public void showTempRes(Game game) {
        Player activePlayer = game.getActivePlayer();
        Resource tempRes = getMasterController().getResourceController().getTempRes().getToHandle();
        ServerUpdate msg = new TempResourceUpdate(activePlayer.getNickname(), tempRes);
        connection.sendMessage(msg);
    }

    @Override
    public void showTempMarbles(Game game, int numWhiteMarbles) {
        Player activePlayer = game.getActivePlayer();
        ServerUpdate msg = new TempMarblesUpdate(activePlayer.getNickname(), numWhiteMarbles, activePlayer.getResTypesAbility());
        connection.sendMessage(msg);
    }

    @Override
    public void showDiscardedCards(SoloGame soloGame, List<DevelopmentCard> cardList) {
        Player activePlayer = soloGame.getActivePlayer();
        ServerUpdate msg = new DiscardedCardsUpdate(activePlayer.getNickname(), cardList);
        connection.sendMessage(msg);
    }

    @Override
    public void showNextActionToken(SoloGame soloGame, SoloActionToken nextToken) {
        Player activePlayer = soloGame.getActivePlayer();
        ServerUpdate msg = new ActionTokenUpdate(activePlayer.getNickname(), nextToken);
        connection.sendMessage(msg);
    }

    @Override
    public void setProductionDone(Game game){
        Player activePlayer = game.getActivePlayer();
        ServerUpdate msg = new ProductionDoneUpdate(activePlayer.getNickname());
        connection.sendMessage(msg);
    }

    @Override
    public void setMainActionDone(Game game) {
        Player activePlayer = game.getActivePlayer();
        ServerUpdate msg = new MainActionDoneUpdate(activePlayer.getNickname());
        connection.sendMessage(msg);
    }
}
