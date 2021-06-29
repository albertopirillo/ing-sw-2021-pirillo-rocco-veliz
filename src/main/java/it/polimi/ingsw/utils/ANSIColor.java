package it.polimi.ingsw.utils;

//https://en.wikipedia.org/wiki/ANSI_escape_code#3-bit_and_4-bit
public interface ANSIColor {
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    String MAGENTA = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String GREY = "\u001B[90m";
    String BRIGHT_RED = "\u001B[91m";
    String BRIGHT_YELLOW = "\u001B[93m";
}
