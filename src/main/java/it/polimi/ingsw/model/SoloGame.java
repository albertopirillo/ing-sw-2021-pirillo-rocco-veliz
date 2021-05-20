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

public class SoloGame extends Game {

    private List<SoloActionToken> soloTokens;
    private int blackCrossPosition;

    public SoloGame(Player player) throws FullCardDeckException {
        //player gets no resources nor faith points in solo game
        super();
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
        super();
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
    public synchronized void nextTurn() throws NegativeResAmountException, InvalidKeyException {
        SoloActionToken currentToken = this.soloTokens.remove(0);
        currentToken.reveal();
        this.soloTokens.add(currentToken);
        this.updateNextActionToken(this.soloTokens.get(0));
        checkEndGame();
    }

    @Override
    public void checkEndGame() throws NegativeResAmountException, InvalidKeyException {
        if(this.blackCrossPosition >= 20) this.lastTurn(false);
    }

    public void updateDiscardedCards(List<DevelopmentCard> cardList){
        //System.out.println("[MODEL] Notifying listeners of card discarded by Lorenzo");
        for(ModelObserver observer : getObservers())
            observer.showDiscardedCards(this, cardList);
    }

    public void updateNextActionToken(SoloActionToken nextToken) {
        //System.out.println("[MODEL] Notifying listeners of solo action token update");
        for(ModelObserver observer : getObservers())
            observer.showNextActionToken(this, nextToken);
    }

    @Override
    public String giveInkwell() {
        Player firstPlayer = this.getPlayersList().get(0);
        firstPlayer.setInkwell(true);
        this.setActivePlayer(firstPlayer);
        return getActivePlayer().getNickname();
    }

    @Override
    public void lastTurn(boolean win) throws NegativeResAmountException, InvalidKeyException {
        //In solo mode, the game ends immediately
        if (win) {
            Map<Player, Integer> map = this.computeFinalScore();
            int finalScore = map.get(this.getActivePlayer());
            //TODO: game is over, send score to the View
        } else {
            //TODO: game is over, tell the View that you lost
        }
    }

    public void startGame() {
        //init leader cards and give them to player
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
            Player player = getPlayersList().get(0);
            List<LeaderCard> chosenCards = new ArrayList<>();
            Set<Integer> selectedIndexes = new HashSet<>();
            while(selectedIndexes.size() < 4){
                Random rnd = new Random();
                selectedIndexes.add(rnd.nextInt(leaderCards.size()));
            }
            for(int id: selectedIndexes){
                LeaderCard chosenCard = leaderCards.get(id);
                chosenCards.add(chosenCard);
            }
            //assign the player the four leader cards he will use for making the selection
            player.setLeaderCards(chosenCards);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initSoloTokens(boolean testing) {
        this.soloTokens = new LinkedList<>();
        this.soloTokens.add(new MoveBlackCross(this));
        this.soloTokens.add(new MoveAndShuffle(this));
        for(CardColor color: CardColor.values()){
            this.soloTokens.add(new DiscardDevCards(this, color));
        }
        if (!testing) {
            shuffleSoloTokens();
        }
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
