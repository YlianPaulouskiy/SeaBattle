package edu.javagroup.seabattle.util;



public class NumberUtils {

    public static boolean isNumber(String number) {
        if (StringUtils.isNotEmpty(number)) {
            if (number.contains(".")) {
                return false;
            } else {
                return !number.contains("-");
            }
        } else {
            return false;
        }
    }

    public static String currentNumber(int number) {
        if (number < 10) {
            return "0".concat(Integer.toString(number));
        } else {
            return Integer.toString(number);
        }
    }

    public static String numberBefore(int number) {
        return currentNumber(number - 1);
    }

    public static String numberAfter(int number) {
        return currentNumber(number + 1);
    }
}
