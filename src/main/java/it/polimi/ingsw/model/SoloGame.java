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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SoloGame extends Game {

    private List<SoloActionToken> soloTokens;

    private int blackCrossPosition;

    public SoloGame(Player player) throws FullCardDeckException {
        //player gets no resources nor faith points in solo game
        this.setActivePlayer(player);
        this.setMarket(new Market());
        this.blackCrossPosition = 0;
        initSoloTokens(false);
        startGame();
    }

    public SoloGame(boolean testing) throws FullCardDeckException {
        this.setActivePlayer(new Player("nickname"));
        this.setMarket(new Market(true));
        this.blackCrossPosition = 0;
        this.soloTokens = new LinkedList<>();
        initSoloTokens(true);
    }

    @Override
    public void nextTurn() {
        SoloActionToken currentToken = this.soloTokens.remove(0);
        currentToken.reveal();
        this.soloTokens.add(currentToken);
    }

    @Override
    public void computeFinalScore() {
        //TODO: implement here
    }

    @Override
    public void endGame() {
        //TODO: implement here
        //TODO: game must end also if the player buys the last devCard of a column, or buys his 7th devCard
        //TODO: can be resolved by overloading buy method in PlayerController?
    }

    @Override
    public ArrayList<Player> getPlayersList() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(this.getActivePlayer());
        return players;
    }

    @Override
    public int getPlayerAmount() {
        return 1;
    }

    private void startGame() {
        //init leader cart and give them to the player
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        List<LeaderCard> leaderCards;
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        try {
            JsonReader reader = new JsonReader(new FileReader("src/main/resources/LeaderCardsConfig.json"));
            leaderCards = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Collections.shuffle(leaderCards);
        leaderCards = leaderCards.subList(0, 4);
        //now player has to choose which cards to keep
        //TODO: this.player.chooseLeaderCards(leaderCards);
    }

    private void initSoloTokens(boolean testing) {
        this.soloTokens = new LinkedList<>();
        this.soloTokens.add(new MoveBlackCross(this));
        this.soloTokens.add(new MoveAndShuffle(this));
        for(CardColor color: CardColor.values())
            this.soloTokens.add(new DiscardDevCards(this, color));
        if (!testing) shuffleSoloTokens();
    }

    public void shuffleSoloTokens() {
        Collections.shuffle(this.soloTokens);
    }

    //This should be used only in testing
    public ArrayList<SoloActionToken> getSoloTokens() {
        return new ArrayList<>(this.soloTokens);
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public void moveBlackCross(int amount) {
        this.blackCrossPosition = this.blackCrossPosition + amount;
    }

}
