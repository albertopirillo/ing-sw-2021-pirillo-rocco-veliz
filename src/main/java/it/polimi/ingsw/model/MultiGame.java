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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MultiGame extends Game {

    private List<LeaderCard> finalDeckLeader;
    private boolean lastTurn;

    public MultiGame(int playerAmount, List<Player> players) throws FullCardDeckException {
        this.setPlayerAmount(playerAmount);
        this.setPlayersList(players);
        this.setMarket(new Market());
        this.lastTurn = false;
        startGame();
    }

    public MultiGame(boolean testing) throws FullCardDeckException {
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

    private void startGame() {
        //init market and devCards
        //init leader cart and give them to players
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsConfig.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<LeaderCard> leaderCards = gson.fromJson(reader, listType);
        //Collections.shuffle((List<LeaderCard>) this.cards); shuffle leaderCards;
        //give 2 leadersCards to player

        finalDeckLeader = new ArrayList<>(leaderCards);
        for (Player pl : this.getPlayersList()) {
            List<LeaderCard> chosenCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Random rnd = new Random();
                int chosenInt = rnd.nextInt(leaderCards.size());
                LeaderCard chosenCard = leaderCards.get(chosenInt);
                leaderCards.remove(chosenInt);
                chosenCards.add(chosenCard);
            }
            //now player has to choose which cards to keep
            List<LeaderCard> returnedCards = giveLeaderCards(pl, chosenCards);
            //insert back not chosen cards (returnedCards)
            leaderCards.addAll(returnedCards);
        }

        // leaderCard arraylist now contains not chosen cards
        // all leader cards are still all in finalDeckLeader with initial order
        // (since we don't have an id in leaderCard json)

        giveInkwell();
    }

    private List<LeaderCard> giveLeaderCards(Player player, List<LeaderCard> leaderCards) {
        // leaderCards is made of the two chosen card out of 4 given at startGame
        //TODO the choice of leaderCards
        List<LeaderCard> returnedCards = new ArrayList<>(); //put here cards not chosen
        player.setLeaderCards(leaderCards);
        return returnedCards; //return leaderCards to insert back in deck for other players to choose
    }

    private void giveInkwell() {
        //First player is chosen randomly
        int numPlayer = new Random().nextInt(this.getPlayerAmount());
        Player firstPlayer = this.getPlayersList().get(numPlayer);
        firstPlayer.setInkwell(true);
        this.setActivePlayer(firstPlayer);
    }

    private void giveResources(Resource resource) {
        // TODO implement here
    }
}