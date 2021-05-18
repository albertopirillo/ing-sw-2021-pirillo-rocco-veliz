package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.ANSIColor;
import java.util.*;
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

    /**
    *  Initializes all three Pope Favor Cards
    */
    private void initPopeCards() {
        for (int i = 1; i < 4; i++) {
            this.popeCards.add(new PopeFavorCard(i));
        }
    }

    /**
     * Player's faith is stored in his own faith track
     * Used to calculate it's position on the track
     * @return integer corresponding to player's faith amount
     */
    public int getPlayerFaith() {
        return playerFaith;
    }

    /**
     * Use this method to add faith points to the players
     * based on their game moves
     * @param amount the amount of faith obtained by the player
     */
    public void addPlayerFaith(int amount) {
        this.playerFaith += amount;
    }

    //check if vaticanReport condition applies

    /**
     * This method is called by PersonalBoard's "UpdateFaithTrack" and checks
     * If player is on a tile that gives Victory Points those are given to him
     * Otherwise if the player is on a Pope Tile (n° 8, 16 or 24) a Vatican Report is activated
     * @param player the current player checked by "UpdateFaithTrack" method
     * @param players the list of players in the current game
     */
    public void checkPopeTile(Player player, List<Player> players) {
        switch (player.getPlayerFaith()) {
            case 8:
            case 16:
            case 24:
                vaticanReport(player, players);
                break;

            //TODO Add cells that give points
        }
    }

    /**
     * This method is used to check if a player is within the last activated Vatican Report
     * region and according to the rules he will also get the Report points
     * @param player the player being checked
     * @param section the currently activated region of the Vatican Report
     * @return True if player is in the region according to the rules
     */
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

    /**
     * This method gives Victory Points to the player that triggered the Vatican Report
     * and flips it's Pope Favor card
     * @param player the player on the Pope Tile (cell n° 8, 16 or 24)
     * @param players the other players used to check if they are within the report region
     */
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
                    try {
                        player.getGame().lastTurn(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } break;
        }
    }

    /**
     * When a Vatican Report is activated by another player, based on the player position this
     * method flips his card and gives him the report points
     * Otherwise, according to the rules, the Vatican Report section get deactivated and the
     * player loses the opportunity to get those extra points
     * @param section The section that has been activated
     * @param players The list of players
     */
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

    /**
     * Used in VaticanReport method to obtain the Pope Favor card corresponding to
     * the report section currently activated
     * @param section The report section currently activated
     * @return The corresponding Pope Favor card
     */
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
        sb.append("\n\t\tPopeTile = |" + ANSIColor.YELLOW + "P" + ANSIColor.RESET + "|");
        sb.append("\n\t\tPlayerMarker = |" + ANSIColor.MAGENTA + "X" +ANSIColor.RESET + "|");
        sb.append("\n\t\tVaticanReportSection = " + ANSIColor.BLUE + "SECTION_ONE " + ANSIColor.RESET + ", " + ANSIColor.RED + "SECTION_TWO " + ANSIColor.RESET + ", " + ANSIColor.GREEN + "SECTION THREE " + ANSIColor.RESET + "\n\n\t");

        for (int i = 1; i < 25; i++) {
            if (4<i && i<9){
                sb.append(ANSIColor.BLUE + "|" + ANSIColor.RESET);
            } else if(11<i && i<17){
                sb.append(ANSIColor.RED + "|" + ANSIColor.RESET);
            } else if (18<i){
                sb.append(ANSIColor.GREEN + "|" + ANSIColor.RESET);
            } else sb.append("|");

            if (i == playerFaith) {
                sb.append(ANSIColor.MAGENTA + "X" + ANSIColor.RESET);
            } else if (i==8 || i==16 || i==24) {
                sb.append(ANSIColor.YELLOW + "P" + ANSIColor.RESET);
            } else { sb.append("-"); }

            if (4<i && i<9){
                sb.append(ANSIColor.BLUE + "| " + ANSIColor.RESET);
            } else if(11<i && i<17){
                sb.append(ANSIColor.RED + "| " + ANSIColor.RESET);
            } else if (18<i){
                sb.append(ANSIColor.GREEN + "| " + ANSIColor.RESET);
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