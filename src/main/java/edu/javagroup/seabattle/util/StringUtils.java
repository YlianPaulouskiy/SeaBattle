package edu.javagroup.seabattle.util;

/**
 * Класс осуществляет операции над строками
 *
 * @author Павловский Юлиан
 */
public class StringUtils {

    /**
     * Метод проверяет является ли пришедшая строка пустой
     *
     * @param line строка
     * @return true, если строка null или ее длина равна 0
     */
    public static boolean isEmpty(CharSequence line) {
        return line == null || line.length() == 0;
    }

    /**
     * Метод проверяет не пуста ли пришедшая строка
     *
     * @param line строка
     * @return инвертированный ответ метода isEmpty()
     */
    public static boolean isNotEmpty(CharSequence line) {
        return !isEmpty(line);
    }

    /**
     * Метод возвращает предыдущий символ в таблице Unicode
     *
     * @param symbol текущий символ
     * @return предыдущий символ
     */
    public static char letterBefore(char symbol) {
        return (char) (symbol - 1);
    }

    /**
     * Метод возвращает следующий символ св таблице Unicode
     *
     * @param symbol текущий символ
     * @return следующий символ
     */
    public static char letterAfter(char symbol) {
        return (char) (symbol + 1);
    }
}
