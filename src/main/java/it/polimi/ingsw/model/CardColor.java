package it.polimi.ingsw.model;

public enum CardColor {
    GREEN,
    BLUE,
    YELLOW,
    PURPLE;

    public int getNumberColumn(){
        switch(this){
            case GREEN:
                return 0;
            case BLUE:
                return 1;
            case YELLOW:
                return 2;
            case PURPLE:
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }
}