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

public class SoloGame extends Game {

    private List<SoloActionToken> soloTokens;

    private int blackCrossPosition;

    public SoloGame(Player player) throws FullCardDeckException {
        //player gets no resources nor faith points in solo game
        this.setActivePlayer(player);
        this.setMarket(new Market());
        this.setPlayerAmount(1);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        this.setPlayersList(playerList);
        this.blackCrossPosition = 0;
        initSoloTokens(false);
        startGame();
    }

    public SoloGame(boolean testing) throws FullCardDeckException {
        Player player = new Player("nickname");
        this.setActivePlayer(player);
        this.setMarket(new Market(true));
        this.setPlayerAmount(1);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        this.setPlayersList(playerList);
        this.blackCrossPosition = 0;
        this.soloTokens = new LinkedList<>();
        initSoloTokens(true);
    }

    @Override
    public void nextTurn() throws NegativeResAmountException, InvalidKeyException {
        SoloActionToken currentToken = this.soloTokens.remove(0);
        currentToken.reveal();
        this.soloTokens.add(currentToken);
        checkEndGame();
    }

    @Override
    public void checkEndGame() throws NegativeResAmountException, InvalidKeyException {
        if(this.blackCrossPosition >= 20) this.lastTurn(false);
    }

    @Override
    public void lastTurn(boolean win) throws NegativeResAmountException, InvalidKeyException {
        //In solo mode, the game ends immediately
        if (win) {
            Map<Player, Integer> map = this.computeFinalScore();
            int finalScore = map.get(this.getActivePlayer());
            //TODO: game is over, send score to the View
        }
        else {
            //TODO: game is over, tell the View that you loss
        }
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
