package it.polimi.ingsw.model;

public enum ResourceType {
    STONE,
    COIN,
    SHIELD,
    SERVANT,
    FAITH
}

//equals() method is not needed, operator "==" can be used to check ResourceType
//ResourceType.values() gives an array with all the class values
//For-each can be used: for (ResourceType res: ResourceType.values()) {...}