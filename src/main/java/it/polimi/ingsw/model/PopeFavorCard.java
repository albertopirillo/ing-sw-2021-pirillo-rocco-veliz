package it.polimi.ingsw.model;

public class PopeFavorCard extends Card {

    private boolean faceUp;
    private boolean reported;
    private VaticanReportSection section;

    public PopeFavorCard(int victoryPoints, int i) {
        super(victoryPoints);
        this.faceUp = false;
        this.reported = false;
        this.section = assignSection(i);
    }
    public PopeFavorCard(int i) {
        super(0);
        this.faceUp = false;
        this.reported = false;
        this.section = assignSection(i);
    }

    private VaticanReportSection assignSection(int i) {
        switch(i){
            case 1: this.section = VaticanReportSection.GROUP_ONE; break;
            case 2: this.section = VaticanReportSection.GROUP_TWO; break;
            case 3: this.section = VaticanReportSection.GROUP_THREE; break;
        }
        return null;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReportedAndFlip(){
        this.reported = true;
        this.faceUp = true;
    }

    public VaticanReportSection getSection(){
        return this.section;
    }
}