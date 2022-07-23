package edu.javagroup.seabattle.util;

/**
 * Класс осуществляет операции над числами
 *
 * @author Павловский Юлиан
 */
public class NumberUtils {

    /**
     * Метод проверяет, содержит ли строка целочисленное, положительное значение
     *
     * @param number число в строквом представлении
     * @return true, если это положительное целочисленное значение
     */
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

    /**
     * Метод принимает число и возвращает его же в строковом
     * представлении и если число < 10 то с лидирующими нулями
     *
     * @param number число
     * @return строка в виде числа с лидирующими нулями
     */
    public static String currentNumber(int number) {
        if (number < 10) {
            return "0".concat(Integer.toString(number));
        } else {
            return Integer.toString(number);
        }
    }

    /**
     * Метод принимает число и возвращает строку в виде предыдущего числа
     * с лидирующими нулями
     *
     * @param number число
     * @return строка в виде предыдущего числа
     */
    public static String numberBefore(int number) {
        return currentNumber(number - 1);
    }

    /**
     * Метод принимает число и возвращает строку в виде предыдущего числа
     * с лидирующими нулями
     *
     * @param number число
     * @return строка в виде следующего числа
     */
    public static String numberAfter(int number) {
        return currentNumber(number + 1);
    }
}
