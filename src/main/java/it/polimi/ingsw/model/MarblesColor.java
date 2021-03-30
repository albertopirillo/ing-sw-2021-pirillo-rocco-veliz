package it.polimi.ingsw.model;

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
}