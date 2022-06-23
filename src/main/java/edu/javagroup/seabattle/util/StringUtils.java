package edu.javagroup.seabattle.util;

public class StringUtils {

    public static boolean isEmpty(CharSequence line) {
        return line == null || line.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence line) {
        return !isEmpty(line);
    }

    public static char letterBefore(char symbol) {
        return (char) (symbol - 1);
    }

    public static char letterAfter(char symbol) {
        return (char) (symbol + 1);
    }
}
