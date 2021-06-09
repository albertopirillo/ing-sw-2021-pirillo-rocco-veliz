package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaithTrack implements Serializable, Cloneable {
    private int playerFaith;
    private List<PopeFavorCard> popeCards;
    private int blackCrossPosition;

    public FaithTrack() {
        this.playerFaith = 0;
        this.popeCards = new ArrayList<>();
        initPopeCards();
        blackCrossPosition = -1;
    }

    /**
    *  Initializes all three Pope Favor Cards
    */
    private void initPopeCards() {
        for (int i = 1; i < 4; i++) {
            this.popeCards.add(new PopeFavorCard(i));
        }
    }

    public List<PopeFavorCard> getPopeFavorCards() { return this.popeCards; }

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
        if (!checkBlackCross(players)){
            switch (player.getPlayerFaith()) {
                case 8, 16, 24 -> vaticanReport(player, players);
            }
        }
    }

    /**
     * This method check if the black cross is activating a vatican report
     * @param players the list of players (in solo game contains only the soloPlayer)
     * @return true if vatican report has been activated
     */
    private boolean checkBlackCross(List<Player> players){
        if (blackCrossPosition > 4){
            switch (blackCrossPosition) {
                case 8 -> {
                    blackVaticanReport(VaticanReportSection.GROUP_ONE, players);
                    return true;
                }
                case 16 -> {
                    blackVaticanReport(VaticanReportSection.GROUP_TWO, players);
                    return true;
                }
                case 24 -> {
                    blackVaticanReport(VaticanReportSection.GROUP_THREE, players);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * When a Vatican Report is activated by the black cross, based on the soloPlayer position this
     * method flips his card and gives him the report points
     * Otherwise, according to the rules, the Vatican Report section get deactivated and the
     * player loses the opportunity to get those extra points
     * @param section the currently activated region of the Vatican Report
     * @param players the list of players (in solo game contains only the soloPlayer)
     */
    private void blackVaticanReport(VaticanReportSection section, List<Player> players){
        Player soloPlayer = players.get(0);
        PopeFavorCard popeFavor = soloPlayer.getPersonalBoard().getFaithTrack().getPopeFavorBySection(section);
        if (soloPlayer.getPersonalBoard().getFaithTrack().inProximityOfVaticanReport(soloPlayer, section) && !popeFavor.isReported()){
            //if pl IS report section assign points and flip card
            popeFavor.setReportedAndFlip(true);
            switch (section) {
                case GROUP_ONE -> soloPlayer.addVictoryPoints(2);
                case GROUP_TWO -> soloPlayer.addVictoryPoints(3);
                case GROUP_THREE -> soloPlayer.addVictoryPoints(4);
            }
        } else {
            //if pl is NOT in report section, deactivate his card
            popeFavor.setReportedAndFlip(false); }
    }


    /**
     * This method is used to check if a player is within the last activated Vatican Report
     * region and according to the rules he will also get the Report points
     * @param player the player being checked
     * @param section the currently activated region of the Vatican Report
     * @return True if player is in the region according to the rules
     */
    private boolean inProximityOfVaticanReport(Player player, VaticanReportSection section) {
        return switch (section) {
            case GROUP_ONE -> player.getPlayerFaith() >= 5;
            case GROUP_TWO -> player.getPlayerFaith() >= 12;
            case GROUP_THREE -> player.getPlayerFaith() >= 19 && player.getPlayerFaith() <= 23;
        };
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
        switch (player.getPlayerFaith()) {
            case 8 -> {
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_ONE);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(2);
                    vaticanReportOthers(VaticanReportSection.GROUP_ONE, players);
                }
            }
            case 16 -> {
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_TWO);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(3);
                    vaticanReportOthers(VaticanReportSection.GROUP_TWO, players);
                }
            }
            case 24 -> {
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_THREE);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip(true);
                    player.addVictoryPoints(4);
                    vaticanReportOthers(VaticanReportSection.GROUP_THREE, players);
                    try {
                        player.getGame().lastTurn(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
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
                        switch (section) {
                            case GROUP_ONE -> pl.addVictoryPoints(2);
                            case GROUP_TWO -> pl.addVictoryPoints(3);
                            case GROUP_THREE -> pl.addVictoryPoints(4);
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
    /**
     * Moves the black cross forward by the specified number of spaces
     * @param amount the number of spaces
     */
    public void moveBlackCross(int amount) {
        this.blackCrossPosition = this.blackCrossPosition + amount;
    }
    /**
     * Gets the position of the black cross, aka Lorenzo's marker
     * @return an int representing the position
     */
    public int getBlackCrossPosition(){
        return this.blackCrossPosition;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\tFaithTrack Legend:");
        sb.append("\n\t\tEmptyTile = |-|");
        sb.append("\n\t\tPopeTile = |" + ANSIColor.YELLOW + "P" + ANSIColor.RESET + "|");
        sb.append("\n\t\tPlayerMarker = |" + ANSIColor.MAGENTA + "X" +ANSIColor.RESET + "|");
        sb.append("\n\t\tVaticanReportSection = " + ANSIColor.BLUE + "SECTION_ONE " + ANSIColor.RESET + ", " + ANSIColor.RED + "SECTION_TWO " + ANSIColor.RESET + ", " + ANSIColor.GREEN + "SECTION THREE " + ANSIColor.RESET);
        if (blackCrossPosition > -1){
            sb.append("\n\t\tLorenzoMarker = |" + ANSIColor.CYAN + "+" + ANSIColor.RESET + "|");
        }
        sb.append("\n\n\t");


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
            } else if (blackCrossPosition > -1 && i == blackCrossPosition){
                sb.append(ANSIColor.CYAN + "+" + ANSIColor.RESET);
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

    /**
     * Clones the current object
     * @return a new Object with the same data
     */
    @Override
    public synchronized FaithTrack clone() {
        FaithTrack clone = null;
        try {
            clone = (FaithTrack) super.clone();
            List<PopeFavorCard> newList = new ArrayList<>();
            for (PopeFavorCard popeCard : this.getPopeFavorCards()) {
                newList.add(popeCard.clone());
            }
            clone.popeCards = newList;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}