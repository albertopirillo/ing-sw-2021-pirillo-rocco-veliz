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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Game implementation used for Multiplayer games
 */
public class MultiGame extends Game {

    private boolean lastTurn;

    /**
     * Constructs a new object, setting the corresponding Market
     */
    public MultiGame() throws FullCardDeckException {
        super();
        this.setMarket(new Market());
    }

    /**
     * Constructs a new object, setting the corresponding Market and other infos
     * @param playerAmount the amount of players in this game
     * @param players a list of users that will play in this game
     */
    public MultiGame(int playerAmount, List<Player> players) throws FullCardDeckException {
        super();
        this.setPlayerAmount(playerAmount);
        this.setPlayersList(players);
        this.setMarket(new Market());
        this.lastTurn = false;
        startGame();
    }

    /**
     * <p>Constructor used in testing</p>
     * <p>Sets the player amount to 4</p>
     * <p>Gives fake names to the player and adds them to the list</p>
     * @param testing true if this is used as intended for testing
     */
    public MultiGame(boolean testing) throws FullCardDeckException {
        super();
        this.setPlayerAmount(4);
        this.setMarket(new Market(testing));
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

    //Selects the new active Player
    @Override
    public synchronized void  nextTurn() throws NegativeResAmountException, InvalidKeyException {
        int index = this.getPlayersList().indexOf(this.getActivePlayer());
        this.setActivePlayer(this.getPlayersList().get((index + 1) % getPlayerAmount()));
        checkEndGame();
    }

    @Override
    public void checkEndGame() throws NegativeResAmountException, InvalidKeyException {
        //Every player until the first one have to play their last turn
        if (lastTurn && this.getActivePlayer().getInkwell()) {
            Map<Player, Integer> finalScores = computeFinalScore();
            List<Player> playerRanks = computeRanks(finalScores);
            Map<String, Integer> scores = new HashMap<>();
            List<String> ranking = new ArrayList<>();
            for(Map.Entry<Player, Integer> entry: finalScores.entrySet()){
                scores.put(entry.getKey().getNickname(), entry.getValue());
            }
            for(Player player: playerRanks){
                ranking.add(player.getNickname());
            }
            notifyEndOfGame(ranking, scores);
        }
    }

    /**
     * <p>Computes the final scores of the players</p>
     * <p>The first element of the returned list is the winner</p>
     * @param finalScores a map with players as keys and final scores as values
     * @return a list with the ranks of all players
     */
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
    //the argument win is only used for solo game
    public void lastTurn(boolean win) {
        this.lastTurn = true;
    }

    @Override
    public void startGame() {
        //init leader cards and give them to players
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();

        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/json/LeaderCardsConfig.json"));
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

    @Override
    public String giveInkwell() {
        //First player is chosen randomly
        int numPlayer = new Random().nextInt(this.getPlayerAmount());
        //numPlayer = 0; //uncomment to disable randomization
        Player firstPlayer = this.getPlayersList().get(numPlayer);
        firstPlayer.setInkwell(true);
        this.setActivePlayer(firstPlayer);
        return getActivePlayer().getNickname();
    }
}