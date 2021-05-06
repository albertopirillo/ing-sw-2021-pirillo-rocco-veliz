package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

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

    @Override
    public String toString() {
        String color = null;
        switch (this) {
            case GREEN: color = ANSIColor.GREEN + "GREEN";break;
            case BLUE: color = ANSIColor.BLUE + "BLUE"; break;
            case YELLOW: color = ANSIColor.BRIGHT_YELLOW + "YELLOW";break;
            case PURPLE:color = ANSIColor.MAGENTA + "PURPLE";break;
        }
        return color.concat(ANSIColor.RESET);
    }
}