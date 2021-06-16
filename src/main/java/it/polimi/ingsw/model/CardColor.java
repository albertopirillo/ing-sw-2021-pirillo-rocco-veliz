package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

public enum CardColor {
    GREEN,
    BLUE,
    YELLOW,
    PURPLE;

    public static CardColor parseColorCard(int num){
        return switch (num) {
            case 1 -> CardColor.GREEN;
            case 2 -> CardColor.BLUE;
            case 3 -> CardColor.YELLOW;
            case 4 -> CardColor.PURPLE;
            default -> null;
        };
    }

    public int getNumberColumn(){
        return switch (this) {
            case GREEN -> 0;
            case BLUE -> 1;
            case YELLOW -> 2;
            case PURPLE -> 3;
        };
    }

    @Override
    public String toString() {
        String color = switch (this) {
            case GREEN -> ANSIColor.GREEN + "GREEN";
            case BLUE -> ANSIColor.BLUE + "BLUE";
            case YELLOW -> ANSIColor.BRIGHT_YELLOW + "YELLOW";
            case PURPLE -> ANSIColor.MAGENTA + "PURPLE";
        };
        return color.concat(ANSIColor.RESET);
    }
}