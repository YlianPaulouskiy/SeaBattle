package edu.javagroup.seabattle.util;

import edu.javagroup.seabattle.constants.Constants;

import java.util.Arrays;

/**
 * Класс содержит методы для проверки ip адресса на валидность
 *
 * @author Павловский Юлиан
 */
public class IpAddressUtils {

    /**
     * Метод проверяет ip адресс на валидность
     *
     * @param ip строка в виде ip адресса
     * @return true, если ip адресс верен
     */
    public static boolean isIpAddress(String ip) {
        try {
            //проверяем ip на пустоту, на начало и на конец с точки и на длину
            if (StringUtils.isEmpty(ip) || ip.startsWith(".") || ip.endsWith(".")
                    || ip.length() > 15) {
                return false;
            }
            //разбиваем ip на массив интов по точке
            int[] ipFragments = Arrays.stream(ip.split("\\."))
                    .mapToInt(Integer::parseInt).toArray();
            //проверяем чтобы ип не начинался на 0 и колличество элементов равнялось 4
            if (ipFragments[0] == 0 || ipFragments.length != 4) {
                return false;
            }
            //проверяем каждый элемент на допустимый диапозон значений
            for (int fragment : ipFragments) {
                if (!allowableNumber(fragment)) {
                    return false;
                }
            }
            //проверяем чтобы ip не соответствовал определенному шаблону
            return !Arrays.equals(ipFragments, Constants.LOCALHOST);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Метод проверяет содержится ли данное число в допустимых пределах
     *
     * @param number число (экземпляр ip адресса)
     * @return true, если число в пределах от 0 до 255
     */
    private static boolean allowableNumber(int number) {
        return NumberUtils.isNumber(Integer.toString(number)) && number >= 0 && number <= 255;
    }

}
