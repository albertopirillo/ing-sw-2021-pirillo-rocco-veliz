package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

public enum MarblesColor {
    WHITE,
    BLUE,
    GREY,
    YELLOW,
    PURPLE,
    RED;

    //Parsing to ResourceType from Marbles(no WHITE)
    public ResourceType getResourceType(){
        switch(this){
            case BLUE:
                return ResourceType.SHIELD;
            case GREY:
                return ResourceType.STONE;
            case YELLOW:
                return ResourceType.COIN;
            case PURPLE:
                return ResourceType.SERVANT;
            case RED:
                return ResourceType.FAITH;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static boolean contains(MarblesColor other) {
        for (MarblesColor res: MarblesColor.values())
            if (other == res) return true;
        return false;
    }

    @Override
    public String toString() {
        String color = null;
        switch (this) {
            case WHITE: color = "WHITE"; break;
            case BLUE: color = ANSIColor.BLUE + "BLUE"; break;
            case GREY: color = ANSIColor.GREY + "GREY";break;
            case YELLOW :color = ANSIColor.BRIGHT_YELLOW + "YELLOW";break;
            case PURPLE: color = ANSIColor.MAGENTA + "PURPLE";break;
            case RED: color = ANSIColor.BRIGHT_RED + "RED";break;
        }
        return color.concat(ANSIColor.RESET);
    }
}