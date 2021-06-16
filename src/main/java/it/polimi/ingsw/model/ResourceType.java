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
        String color = switch (this) {
            case STONE -> ANSIColor.GREY + "STONE";
            case COIN -> ANSIColor.BRIGHT_YELLOW + "COIN";
            case SHIELD -> ANSIColor.BLUE + "SHIELD";
            case SERVANT -> ANSIColor.MAGENTA + "SERVANT";
            case FAITH -> ANSIColor.BRIGHT_RED + "FAITH";
            case ALL -> ANSIColor.GREEN + "ALL";
        };
        return color.concat(ANSIColor.RESET);
    }
}

//equals() method is not needed, operator "==" can be used to check ResourceType
//ResourceType.values() gives an array with all the class values
//For-each can be used: for (ResourceType res: ResourceType.values()) {...}