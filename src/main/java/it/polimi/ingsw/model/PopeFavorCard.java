package it.polimi.ingsw.model;

public class PopeFavorCard extends Card {

    private boolean faceUp;
    private boolean reported;
    private VaticanReportSection section;

    public PopeFavorCard(int victoryPoints, int i) {
        super(victoryPoints);
        this.faceUp = false;
        this.reported = false;
        assignSection(i);
    }
    public PopeFavorCard(int i) {
        super(0);
        this.faceUp = false;
        this.reported = false;
        assignSection(i);
    }

    private void assignSection(int i) {
        switch(i){
            case 1: this.section = VaticanReportSection.GROUP_ONE; break;
            case 2: this.section = VaticanReportSection.GROUP_TWO; break;
            case 3: this.section = VaticanReportSection.GROUP_THREE; break;
        }
    }

    public boolean isReported() {
        return this.reported;
    }

    public boolean isFaceUp() { return this.faceUp; }

    //IF PLAYER IS IN REPORT SECTION SET REPORTED AND FLIP CARD, IF NOT DON'T FLIP BUT STILL SET REPORTED
    public void setReportedAndFlip(boolean faceUp){
        this.reported = true;
        this.faceUp = faceUp;
    }

    public VaticanReportSection getSection(){
        return this.section;
    }
}