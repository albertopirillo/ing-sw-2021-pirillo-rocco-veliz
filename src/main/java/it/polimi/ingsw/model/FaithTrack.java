package it.polimi.ingsw.model;

import java.util.ArrayList;

public class FaithTrack {

    public FaithTrack() {
        this.popeCards = new ArrayList<>();
    }


    // SINGLE PLAYER
    // private int blackCrossPosition;

    private ArrayList<PopeFavorCard> popeCards;

    private void initPopeCards(){
        for(int i=0; i<3; i++){
            this.popeCards.add(new PopeFavorCard(++i));
        }
    }

    //check if vaticanReport condition applies
    public void checkPopeTile(Player player) {
        switch (player.getPlayerFaith()){
            case 8: case 16: case 24:
                vaticanReport(player);
                break;
        }
    }

    private void vaticanReport(Player player) {
        // Triggered by checkPopeTile
        // gives points according to group value and changes hasReportHappened
        PopeFavorCard popeFavor;
        switch(player.getPlayerFaith()){
            case 8:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_ONE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    player.setVictoryPoints(2);
                } break;
            case 16:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_TWO);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    player.setVictoryPoints(3);
                } break;
            case 24:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_THREE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    player.setVictoryPoints(4);
                } break;
        }
    }

    private PopeFavorCard getPopeFavorBySection(VaticanReportSection section) {
        for (PopeFavorCard popeFavor: popeCards){
            if( popeFavor.getSection() == section){
                return popeFavor;
            }
        }
        return null;
    }

}