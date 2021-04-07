package it.polimi.ingsw.model;

import java.util.ArrayList;

public class FaithTrack {

    public FaithTrack() {
        this.markerPosition = 0;
        this.popeCards = new ArrayList<>();
    }

    private int markerPosition;

    private int blackCrossPosition;

    private ArrayList<PopeFavor> popeCards;

    private void initPopeCards(){
        for(int i=0; i<3; i++){
            this.popeCards.add(new PopeFavor(++i));
        }
    }

    public void setPosition(int pos) {
        int playerFaith = Player.getPlayerFaith();
        if (markerPosition + playerFaith < 24){
            markerPosition = markerPosition + playerFaith;
            checkPopeTile(markerPosition);
        }
        else endgame();
    }

    public void checkPopeTile(int markerPosition) {
        switch (markerPosition){
            case 8: case 16: case 24:
                vaticanReport();
            break;
        }
    }

    private void vaticanReport() {
        // Triggered by checkPopeTile
        // gives points according to group value and changes hasReportHappened
        PopeFavor popeFavor;
        switch(markerPosition){
            case 8:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_ONE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    Player.setVictoryPoints(2);
                } break;
            case 16:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_TWO);
                if (!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    Player.setVictoryPoints(3);
                } break;
            case 24:
                popeFavor = getPopeFavorBySection(VaticanReportSection.GROUP_THREE);
                if(!popeFavor.isReported()) {
                    popeFavor.setReportedAndFlip();
                    Player.setVictoryPoints(4);
                } break;
        }
    }

    private PopeFavor getPopeFavorBySection(VaticanReportSection section) {
        for (PopeFavor popeFavor: popeCards){
            if( popeFavor.getSection() == section){
                return popeFavor;
            }
        }
        //TODO Add exception
        return null;
    }

    private void endgame() {
        // checks if player is on tile n° 24
        if(markerPosition==24){
            //TO IMPLEMENT
        }
    }

}