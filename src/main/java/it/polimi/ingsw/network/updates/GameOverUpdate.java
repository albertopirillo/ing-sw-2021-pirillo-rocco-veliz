package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

import java.util.List;
import java.util.Map;

public class GameOverUpdate extends ServerUpdate{
    private final boolean win;
    private final Map<String, Integer> scores;
    private final List<String> ranking;

    public GameOverUpdate(String activePlayer, boolean win, List<String> ranking, Map<String, Integer> scores) {
        super(activePlayer);
        this.win = win;
        this.scores = scores;
        this.ranking = ranking;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateGameOver(this);
    }

    public boolean isWin(){
        return win;
    }

    public List<String> getRanking(){
        return ranking;
    }

    public Map<String, Integer> getScores(){
        return scores;
    }
}
