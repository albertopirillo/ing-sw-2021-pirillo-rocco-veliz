package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.ANSIColor;

/**
 * Enum with all resource types
 */
public enum ResourceType {
    STONE,
    COIN,
    SHIELD,
    SERVANT,
    FAITH,
    ALL;

    @Override
    public String toString() {
        String color = null;
        switch (this) {
            case STONE: color = ANSIColor.GREY + "STONE";break;
            case COIN: color = ANSIColor.BRIGHT_YELLOW + "COIN"; break;
            case SHIELD: color = ANSIColor.BLUE + "SHIELD";break;
            case SERVANT:color = ANSIColor.MAGENTA + "SERVANT";break;
            case FAITH: color = ANSIColor.BRIGHT_RED + "FAITH";break;
            case ALL: color = ANSIColor.GREEN + "ALL";break;
        }
        return color.concat(ANSIColor.RESET);
    }
}

//equals() method is not needed, operator "==" can be used to check ResourceType
//ResourceType.values() gives an array with all the class values
//For-each can be used: for (ResourceType res: ResourceType.values()) {...}