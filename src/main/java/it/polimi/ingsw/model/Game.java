package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.utils.LeaderAbilityDeserializer;
import it.polimi.ingsw.utils.LeaderCardJsonDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private static int gamesCount = 0;

    private final int gameID;

    private List<Player> players;

    private Player activePlayer;

    private List<LeaderCard> finalDeckLeader;

    private final int playerAmount;

    private boolean lastTurn;

    private final Market market;

    public Game(int playerAmount, List<Player> players) throws FullCardDeckException {
        this.lastTurn = false;
        this.playerAmount = playerAmount;
        this.players = players;
        this.gameID = Game.gamesCount + 1;
        this.market = new Market();
        Game.gamesCount++;
        startGame();
    }

    public Game(boolean testing) throws FullCardDeckException {
        this.lastTurn = false;
        this.playerAmount = 4;
        this.gameID = Game.gamesCount + 1;
        this.market = new Market(testing);
        Game.gamesCount++;
        Player player1 = new Player("a");
        Player player2 = new Player("b");
        Player player3 = new Player("c");
        Player player4 = new Player("d");
        this.players = new ArrayList<>();
        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);
        this.players.add(player4);
        startGame();
    }

    public void startGame() {
        //init market and devCards
        //init leader cart and give them to players
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>() {
        }.getType();
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
        for (Player pl : players) {
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
        game();
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public Market getMarket() {
        return this.market;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    public void game() {
        // TODO implement here
    }

    public void calculatePoints() {
        // TODO implement here
    }

    public void startSolo() {
        // TODO implement here
    }

    public void endSolo() {
        // TODO implement here
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
        int numPlayer = new Random().nextInt(playerAmount);
        Player firstPlayer = this.players.get(numPlayer);
        firstPlayer.setInkwell(true);
        this.activePlayer = firstPlayer;
    }

    //Selects the new active Player
    public void nextTurn() {
        int index = this.players.indexOf(this.activePlayer);
        this.activePlayer = this.players.get((index + 1) % playerAmount);
    }

    private void giveResources(Resource resource) {
        // TODO implement here
    }
}