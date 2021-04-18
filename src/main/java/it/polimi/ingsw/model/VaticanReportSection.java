package it.polimi.ingsw.model;

public enum VaticanReportSection {
    GROUP_ONE ("Group_ONE is from 5 to 8"),
    GROUP_TWO ("Group_TWO is from 12 to 16"),
    GROUP_THREE ("Group_TWO is from 18 to 24");

    private String group;

    VaticanReportSection(String string){
        this.group = string;
    }
}
