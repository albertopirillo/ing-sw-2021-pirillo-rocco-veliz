package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaithTrack implements Serializable {
    private int playerFaith;
    private final List<PopeFavorCard> popeCards;

    public FaithTrack() {
        this.playerFaith = 0;
        this.popeCards = new ArrayList<>();
        initPopeCards();
    }

    private void initPopeCards() {
        for (int i = 1; i < 4; i++) {
            this.popeCards.add(new PopeFavorCard(i));
        }
    }

    public int getPlayerFaith() {
        return playerFaith;
    }

    public void addPlayerFaith(int amount) {
        this.playerFaith += amount;
    }

    //check if vaticanReport condition applies
    public void checkPopeTile(Player player, List<Player> players) {
        switch (player.getPlayerFaith()) {
            case 8:
            case 16:
            case 24:
                vaticanReport(player, players);
                break;
        }
    }

    private boolean inProximityOfVaticanReport(Player player, VaticanReportSection section) {
        switch (section) {
            case GROUP_ONE:
                return player.getPlayerFaith() >= 5;
            case GROUP_TWO:
                return player.getPlayerFaith() >= 12;
            case GROUP_THREE:
                return player.getPlayerFaith() >= 19 && player.getPlayerFaith() <= 23;
        }
        return false;
    }

    private void vaticanReport(Player player, List<Player> players) {
        // Triggered by checkPopeTile
        // gives points according to group value and changes hasReportHappened
        PopeFavorCard popeFavor;
        switch(player.getPlayerFaith()){
            case 8:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_ONE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(2);
                    vaticanReportOthers(VaticanReportSection.GROUP_ONE, players);
                }
                break;
            case 16:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_TWO);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(3);
                    vaticanReportOthers(VaticanReportSection.GROUP_TWO, players);
                } break;
            case 24:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_THREE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(4);
                    vaticanReportOthers(VaticanReportSection.GROUP_THREE, players);
                } break;
        }
    }

    private void vaticanReportOthers(VaticanReportSection section, List<Player> players){
        //CHECK OTHER PLAYERS
        for(Player pl: players){
            PopeFavorCard popeFavor = pl.getPersonalBoard().getFaithTrack().getPopeFavorBySection(section);
            //check if is not activePlayer because activePlayer is already checked
            if(!(pl.getTurn())){
                //check if report hasn't already occurred for that card
                if(!popeFavor.isReported()) {
                    if(pl.getPersonalBoard().getFaithTrack().inProximityOfVaticanReport(pl, section)){
                        //if pl IS report section assign points and flip card
                        popeFavor.setReportedAndFlip(true);
                        switch(section){
                            case GROUP_ONE: pl.addVictoryPoints(2); break;
                            case GROUP_TWO: pl.addVictoryPoints(3); break;
                            case GROUP_THREE: pl.addVictoryPoints(4); break;
                        }
                    } else {
                        //if pl is NOT in report section, deactivate his card
                        popeFavor.setReportedAndFlip(false); }
                }
            }
        }
    }

    public PopeFavorCard getPopeFavorBySection(VaticanReportSection section) {
        for (PopeFavorCard popeFavor: popeCards){
            if( popeFavor.getSection() == section){
                return popeFavor;
            }
        }
        return null;
    }

    public String toString(){
        return "Test faith track print";
    }

}