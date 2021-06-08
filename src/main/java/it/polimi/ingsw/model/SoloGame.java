package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.LeaderAbilityDeserializer;
import it.polimi.ingsw.utils.LeaderCardJsonDeserializer;
import it.polimi.ingsw.utils.ModelObserver;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Game implementation used for single player games
 */
public class SoloGame extends Game {

    private List<SoloActionToken> soloTokens;

    /**
     * Constructs a new object and initializes the action tokens and the market
     * @param player the player of the SoloGame
     */
    public SoloGame(Player player) throws FullCardDeckException {
        //player gets no resources nor faith points in solo game
        super();
        this.setActivePlayer(player);
        this.setMarket(new Market());
        this.setPlayerAmount(1);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        this.setPlayersList(playerList);
        initSoloTokens(false);
        startGame();
    }

    /**
     * <p>Constructor used for testing</p>
     * <p>Sets a fake nickname for the only player</p>
     * @param testing true if this is used, as intended, for testing
     */
    public SoloGame(boolean testing) throws FullCardDeckException {
        super();
        System.out.println("Testing: "+ testing);
        Player player = new Player("nickname");
        this.setActivePlayer(player);
        this.setMarket(new Market(true));
        this.setPlayerAmount(1);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        this.setPlayersList(playerList);
        this.soloTokens = new LinkedList<>();
        initSoloTokens(true);
        moveBlackCross(1);
    }

    @Override
    public synchronized void nextTurn() throws NegativeResAmountException {
        SoloActionToken currentToken = this.soloTokens.remove(0);
        currentToken.reveal();
        this.soloTokens.add(currentToken);
        this.updateLastActionToken(currentToken);
        checkEndGame();
        //to test the end game scenario
        //lastTurn(false);
    }

    @Override
    public void checkEndGame() throws NegativeResAmountException {
        if(getActivePlayer().getPersonalBoard().getFaithTrack().getBlackCrossPosition() == 24){
            lastTurn(false);
        }
    }

    /**
     * Notifies the observers that Lorenzo discarded two development cards
     * @param cardList a list with the two cards that were discarded
     */
    public void updateDiscardedCards(List<DevelopmentCard> cardList){
        for(ModelObserver observer : getObservers()) {
            observer.showDiscardedCards(this, cardList);
            observer.showMarket(this);
        }
    }

    /**
     * Notifies the observer that the action tokens have been updated
     * @param lastToken the next action token on the queue
     */
    public void updateLastActionToken(SoloActionToken lastToken) {
        for(ModelObserver observer : getObservers()) {
            observer.showFaithTrack(this);
            observer.showLastActionToken(this, lastToken);
        }
    }

    @Override
    public String giveInkwell() {
        Player firstPlayer = this.getPlayersList().get(0);
        firstPlayer.setInkwell(true);
        this.setActivePlayer(firstPlayer);
        return getActivePlayer().getNickname();
    }

    @Override
    public void lastTurn(boolean win) throws NegativeResAmountException {
        //In solo mode, the game ends immediately
        List<String> ranking = new ArrayList<>();
        String nickname = this.getActivePlayer().getNickname();
        Map<Player, Integer> map = this.computeFinalScore();
        int finalScore = map.get(this.getActivePlayer());
        Map<String, Integer> scores = new HashMap<>();
        ranking.add(nickname);
        scores.put(nickname, finalScore);
        for(ModelObserver observer : getObservers()) {
            observer.notifyGameOver(this, win, ranking, scores);
        }
    }

    @Override
    public void startGame() {
        //init leader cards and give them to player
        GsonBuilder builder = new GsonBuilder();
        //add adapter to deserialize from abstract class
        builder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilityDeserializer());
        builder.registerTypeAdapter(LeaderCard.class, new LeaderCardJsonDeserializer());
        Gson gson = builder.create();
        Type listType = new TypeToken<List<LeaderCard>>(){}.getType();
        Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/json/LeaderCardsConfig.json")));
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
        moveBlackCross(1);
    }

    private void initSoloTokens(boolean testing) {
        this.soloTokens = new LinkedList<>();
        this.soloTokens.add(new MoveBlackCrossToken(this));
        this.soloTokens.add(new MoveAndShuffleToken(this));
        for(CardColor color: CardColor.values()){
            this.soloTokens.add(new DiscardDevCardsToken(this, color));
        }
        if (!testing) {
            shuffleSoloTokens();
        }
    }

    /**
     * Shuffles the current action token queue
     */
    public void shuffleSoloTokens() {
        Collections.shuffle(this.soloTokens);
    }
    /**
     * <p>Gets the solo tokens queue</p>
     * <p>Should be used only for testing</p>
     * @return the action tokens queue
     */
    protected ArrayList<SoloActionToken> getSoloTokens() {
        return new ArrayList<>(this.soloTokens);
    }
    /**
     * Gets the position of the black cross, aka Lorenzo's marker
     * @return an int representing the position
     */
    public int getBlackCrossPosition() {
        return getActivePlayer().getPersonalBoard().getFaithTrack().getBlackCrossPosition();
    }
    /**
     * Moves the black cross forward by the specified number of spaces
     * @param amount the number of spaces
     */
    public void moveBlackCross(int amount) {
        Player player = getPlayersList().get(0);
        player.getPersonalBoard().getFaithTrack().moveBlackCross(amount);
    }
}
