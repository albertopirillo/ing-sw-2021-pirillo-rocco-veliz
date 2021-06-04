package it.polimi.ingsw.model;

public class PopeFavorCard extends Card implements Cloneable {

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
        switch (i) {
            case 1 -> this.section = VaticanReportSection.GROUP_ONE;
            case 2 -> this.section = VaticanReportSection.GROUP_TWO;
            case 3 -> this.section = VaticanReportSection.GROUP_THREE;
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

    @Override
    public PopeFavorCard clone() {
        PopeFavorCard popeFavorCard = null;
        try {
            popeFavorCard = (PopeFavorCard) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return popeFavorCard;
    }
}