package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaithTrack implements Serializable {
    private int playerFaith;
    private final List<PopeFavorCard> popeCards;

    //LIST OF ANSI CODES FOR COLORED OUTPUT ON CLI
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

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
        StringBuilder sb = new StringBuilder();
        sb.append("\n\tFaithTrack Legend:");
        sb.append("\n\t\tEmptyTile = |-|");
        sb.append("\n\t\tPopeTile = |" + ANSI_YELLOW + "P" + ANSI_RESET + "|");
        sb.append("\n\t\tPlayerMarker = |" + ANSI_PURPLE + "X" + ANSI_RESET + "|");
        sb.append("\n\t\tVaticanReportSection = " + ANSI_BLUE + "SECTION_ONE " + ANSI_RESET + ", " + ANSI_RED + "SECTION_TWO " + ANSI_RESET + ", " + ANSI_GREEN + "SECTION THREE " + ANSI_RESET + "\n\n\t");

        for (int i = 1; i < 25; i++) {
            if (4<i && i<9){
                sb.append(ANSI_BLUE + "|" + ANSI_RESET);
            } else if(11<i && i<17){
                sb.append(ANSI_RED + "|" + ANSI_RESET);
            } else if (18<i){
                sb.append(ANSI_GREEN + "|" + ANSI_RESET);
            } else sb.append("|");

            if (i == playerFaith) {
                sb.append(ANSI_PURPLE + "X" + ANSI_RESET);
            } else if (i==8 || i==16 || i==24) {
                sb.append(ANSI_YELLOW + "P" + ANSI_RESET);
            } else { sb.append("-"); }

            if (4<i && i<9){
                sb.append(ANSI_BLUE + "| " + ANSI_RESET);
            } else if(11<i && i<17){
                sb.append(ANSI_RED + "| " + ANSI_RESET);
            } else if (18<i){
                sb.append(ANSI_GREEN + "| " + ANSI_RESET);
            } else sb.append("| ");
        }

        sb.append("\n\n\tActivated Vatican Reports: ");
        boolean flag = false;
        for(PopeFavorCard popeFavorCard : popeCards){
            if(popeFavorCard.isReported()){
                sb.append(popeFavorCard.getSection()).append(" ");
                flag = true;
            }
        }
        if(!flag) sb.append("No report has yet occurred");
        sb.append("\n\tPope Favor cards that are face up: ");
        flag = false;
        for(PopeFavorCard popeFavorCard : popeCards){
            if(popeFavorCard.isFaceUp()){
                sb.append(popeFavorCard.getSection()).append(" ");
                flag = true;
            }
        }
        if(!flag) sb.append("You have no cards face up");

        return sb.toString();
    }

}