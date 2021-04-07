package it.polimi.ingsw.model;

public class Game {

    public Game() {
    }

    private Player[] players;

    private int playerAmount;

    private boolean lastTurn;

    private static boolean[] hasReportHappened;

    private Market market;

    public void startGame() {
        // TODO implement here
    }

    public Market getMarket() {
        return this.market;
    }

    public void game() {
        // TODO implement here
    }

    public void calculatePoints() {
        // TODO implement here
    }

    public void startSolo() {
        // TODO implement here
    }

    public void endSolo() {
        // TODO implement here
    }

    private void giveLeaderCards(Player player) {
        // TODO implement here
    }

    private void giveInkwell(Player[] player) {
        // TODO implement here
    }

    private void giveResources(Player[] player) {
        // TODO implement here
    }

    private void nextTurn(Player[] player) {
        // TODO implement here
    }

    private void initializeMarketCards(DevelopmentCard[] cards) {
        // TODO implement here
    }

    private void initializeMarketMarbles(MarblesColor[] marbles) {
        // TODO implement here
    }

    private void createFaithTrack(Player player) {
        // TODO implement here
    }

    //checks if report is already occured in that group
    public static boolean checkReport(int group) {return hasReportHappened[group];}

    public static void setReport(int group) {
        if (group == 1) {
            hasReportHappened[1] = true;
        }
        if (group == 2) {
            hasReportHappened[2] = true;
        }
        if (group == 2) {
            hasReportHappened[3] = true;
        }
    }
    }