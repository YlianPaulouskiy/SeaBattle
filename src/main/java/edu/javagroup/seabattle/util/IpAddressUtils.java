package edu.javagroup.seabattle.util;

import edu.javagroup.seabattle.constants.Constants;

import java.util.Arrays;
import java.util.stream.IntStream;

public class IpAddressUtils {

    public static boolean isIpAddress(String ip) {
        try {
            if (StringUtils.isEmpty(ip) || ip.startsWith(".") || ip.endsWith(".")) return false;
            int[] ipFragments = Arrays.stream(ip.split("\\.")).mapToInt(Integer::parseInt).toArray();
            if (ipFragments[0] == 0 || ipFragments.length != 4) return false;
            for (int fragment : ipFragments) {
                if (!allowableNumber(fragment)) return false;
            }
            return !Arrays.equals(ipFragments, Constants.LOCALHOST);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return false;
        }
    }

    private static boolean allowableNumber(int number) {
        return NumberUtils.isNumber(Integer.toString(number)) && number >= 0 && number <= 255;
    }

}
