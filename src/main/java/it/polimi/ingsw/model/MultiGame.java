package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.LeaderAbilityDeserializer;
import it.polimi.ingsw.utils.LeaderCardJsonDeserializer;
import it.polimi.ingsw.utils.ModelObserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class MultiGame extends Game {

    private List<ModelObserver> observers;
    private boolean lastTurn;

    public MultiGame() throws FullCardDeckException {
        this.setMarket(new Market());
        this.observers = new ArrayList<>();
    }

    public MultiGame(int playerAmount, List<Player> players) throws FullCardDeckException {
        this.setPlayerAmount(playerAmount);
        this.setPlayersList(players);
        this.setMarket(new Market());
        this.observers = new ArrayList<>();
        this.lastTurn = false;
        startGame();
    }

    public MultiGame(boolean testing) throws FullCardDeckException {
        this.setPlayerAmount(4);
        this.setMarket(new Market(testing));
        this.observers = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        players.add(new Player("a"));
        players.add(new Player("b"));
        players.add(new Player("c"));
        players.add(new Player("d"));
        this.setPlayersList(players);
        this.lastTurn = false;
        for(Player p: this.getPlayersList()) p.setGame(this);
        startGame();
    }

    public void addObserver(ModelObserver observer) {
        this.observers.add(observer);
    }

    //Selects the new active Player
    @Override
    public void nextTurn() throws NegativeResAmountException, InvalidKeyException {
        int index = this.getPlayersList().indexOf(this.getActivePlayer());
        this.setActivePlayer(this.getPlayersList().get((index + 1) % getPlayerAmount()));
        checkEndGame();
    }

    @Override
    public void checkEndGame() throws NegativeResAmountException, InvalidKeyException {
        //Every player until the first one have to play their last turn
        if (lastTurn && this.getActivePlayer().getInkwell()) {
            List<Player> playerRanks = this.computeRanks(computeFinalScore());
            //TODO: the games is over, send results to Views
        }
    }

    //Returns a list with the player ranks, first element is the winner
    public List<Player> computeRanks(Map<Player, Integer> finalScores) {
        List<Player> sortedPlayers = new ArrayList<>();
        Player maxPlayer = null;
        //Repeat one time for each player
        while(finalScores.size() != 0) {
            //Find the player with the highest score
            int maxScore = 0;
            for (Player p : finalScores.keySet()) {
                if (finalScores.get(p) >= maxScore) {
                    maxScore = finalScores.get(p);
                    maxPlayer = p;
                }
            }
            //Remove that player from the map and add it to the list
            finalScores.remove(maxPlayer);
            sortedPlayers.add(maxPlayer);
        }
        return sortedPlayers;
    }

    @Override
    public void lastTurn(boolean win) {
        this.lastTurn = true;
    }

    public void startGame() {
        //init leader cards and give them to players
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();

        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsConfig.json"));
            List<LeaderCard> leaderCards = gson.fromJson(reader, listType);

            //select 4 leadersCards. We must make sure to select four different cards
            List<LeaderCard> finalDeckLeaderCards = new ArrayList<>(leaderCards);
            for (Player player : this.getPlayersList()) {
                List<LeaderCard> chosenCards = new ArrayList<>();
                Set<Integer> chosenIds = new HashSet<>();
                Set<Integer> selectedIndexes = new HashSet<>();
                if(finalDeckLeaderCards.size() > 4){
                    while(selectedIndexes.size() < 4){
                        Random rnd = new Random();
                        selectedIndexes.add(rnd.nextInt(finalDeckLeaderCards.size()));
                    }
                    for(int id: selectedIndexes){
                        LeaderCard chosenCard = finalDeckLeaderCards.get(id);
                        chosenCards.add(chosenCard);
                        chosenIds.add(chosenCard.getId());
                    }
                    //remove from finalDeckLeaderCards the selected cards
                    finalDeckLeaderCards = new ArrayList<>();
                    for(LeaderCard card: leaderCards){
                        Integer id = card.getId();
                        if(!chosenIds.contains(id)){
                            finalDeckLeaderCards.add(card);
                        }
                    }
                } else {
                    //finalDeckLeaderCards contains the last 4 cards
                    chosenCards.addAll(finalDeckLeaderCards);
                }
                //assign the player the four leader cards he will use for making the selection
                player.setLeaderCards(chosenCards);
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        giveInkwell();
    }

    public String giveInkwell() {
        //First player is chosen randomly
        int numPlayer = new Random().nextInt(this.getPlayerAmount());
        Player firstPlayer = this.getPlayersList().get(numPlayer);
        firstPlayer.setInkwell(true);
        this.setActivePlayer(firstPlayer);
        return getActivePlayer().getNickname();
    }

    public void updateClientModel() {
        System.out.println("[MODEL] Notifying listeners of board update");
        for(ModelObserver observer : observers)
            observer.gameStateChange(this);
    }

    public void updateInitResources(int numPlayer){
        System.out.println("[MODEL] Notifying listeners of players init resources update");
        for(ModelObserver observer : observers)
            observer.notifyInitResources(this, numPlayer);
    }

    public void updateInitLeaderCards(){
        System.out.println("[MODEL] Notifying listeners of players init leaders cards update");
        for(ModelObserver observer : observers)
            observer.notifyInitLeaderCards(this);
    }

    public void showFaithTrack(){
        System.out.println("[MODEL] Notifying listeners of faith track info request");
        for(ModelObserver observer : observers)
            observer.showFaithTrack();
    }

    public void showLeaderCards(String errorMsg){
        System.out.println("[MODEL] Notifying listeners of leader cards info request");
        for(ModelObserver observer : observers)
            observer.showLeaderCards(errorMsg);
    }


}