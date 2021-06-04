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
        return switch (this) {
            case BLUE -> ResourceType.SHIELD;
            case GREY -> ResourceType.STONE;
            case YELLOW -> ResourceType.COIN;
            case PURPLE -> ResourceType.SERVANT;
            case RED -> ResourceType.FAITH;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getName(){
        return switch (this) {
            case BLUE -> "shieldMarble";
            case GREY -> "stoneMarble";
            case YELLOW -> "coinMarble";
            case PURPLE -> "servantMarble";
            case RED -> "faithMarble";
            case WHITE -> "whiteMarble";
        };
    }

    public static boolean contains(MarblesColor other) {
        for (MarblesColor res: MarblesColor.values())
            if (other == res) return true;
        return false;
    }

    @Override
    public String toString() {
        String color = switch (this) {
            case WHITE -> "WHITE";
            case BLUE -> ANSIColor.BLUE + "BLUE";
            case GREY -> ANSIColor.GREY + "GREY";
            case YELLOW -> ANSIColor.BRIGHT_YELLOW + "YELLOW";
            case PURPLE -> ANSIColor.MAGENTA + "PURPLE";
            case RED -> ANSIColor.BRIGHT_RED + "RED";
        };
        return color.concat(ANSIColor.RESET);
    }
}