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
import java.util.List;
import java.util.Random;

public class Game {

    private Player[] players;
    private int playerAmount;
    private boolean lastTurn;
    private Market market;

    public Game(int playerAmount, Player[] players) throws FullCardDeckException {
        this.playerAmount = playerAmount;
        this.players = players;
        startGame();
    }

    public void startGame() throws FullCardDeckException {
        //init market and devCards
        this.market = new Market();
        //init leader cart and give them to players
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/leaderCardsConfig.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<LeaderCard> leaderCards = gson.fromJson(reader, listType);
        //Collections.shuffle((List<LeaderCard>) this.cards); shuffle leaderCards;
        //give 2 leadersCards to player
        for (int i = 0; i < playerAmount; i++) {
            //each player choose 2 cards from 4
            giveLeaderCards(players[i], new LeaderCard[]{
                    leaderCards.get(i),
                    leaderCards.get(i+playerAmount),
                    leaderCards.get(i+playerAmount*2),
                    leaderCards.get(i+playerAmount*3)
            });
        }
        giveInkwell();
        game();
    }

    public Market getMarket() {
        return this.market;
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

    private void giveLeaderCards(Player player, LeaderCard[] leaderCards) {
        // TODO implement here
        player.setLeaderCards(leaderCards);
    }

    private void giveInkwell() {
        int numPlayer = new Random().nextInt(playerAmount);
        this.players[numPlayer].setInkwell(true);//player is choose random
        //set the first player(that has the inkwell) as the element 0 of player[]
        if(numPlayer!=0){
            Player[] p = new Player[playerAmount];
            for (int i = 0; i < playerAmount; i++) {
                p[i] = players[numPlayer+ i % playerAmount];
            }
            players = p;
        }
    }

    private void giveResources(Resource resource) {
        // TODO implement here
    }

    private void nextTurn(Player[] player) {
        // TODO implement here
    }

    private void initializeMarketCards(DevelopmentCard[] cards) {
        // TODO implement here
    }

    private void initializeMarketMarbles(MarblesColor[] marbles) {
        // TODO implement here
    }

    private void createFaithTrack(Player player) {
        // TODO implement here
    }

    }