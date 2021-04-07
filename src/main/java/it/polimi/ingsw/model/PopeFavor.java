package it.polimi.ingsw.model;

public class PopeFavor extends Card {

    private boolean faceUp;
    private boolean reported;
    private VaticanReportSection section;

    public PopeFavor(int victoryPoints, boolean faceUp, int i) {
        super(victoryPoints);
        this.faceUp = false;
        this.reported = false;
        this.section = assignSection(i);
    }
    public boolean isFaceUp() {
        return faceUp;
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