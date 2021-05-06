package it.polimi.ingsw.utils;

//https://en.wikipedia.org/wiki/ANSI_escape_code#3-bit_and_4-bit
public interface ANSIColor {
    String RESET = "\u001B[0m";
    String BLACK = "\u001B[30m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    String MAGENTA = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String WHITE = "\u001B[37m";
    String GREY = "\u001B[90m";
    String BRIGHT_RED = "\u001B[91m";
    String BRIGHT_GREEN = "\u001B[92m";
    String BRIGHT_YELLOW = "\u001B[93m";
    String BRIGHT_BLUE = "\u001B[94m";
    String BRIGHT_MAGENTA = "\u001B[95m";
    String BRIGHT_CYAN = "\u001B[96m";
    String BRIGHT_WHITE = "\u001B[97m";
}
