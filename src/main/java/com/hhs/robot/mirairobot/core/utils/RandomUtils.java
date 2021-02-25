package com.hhs.robot.mirairobot.core.utils;

import java.util.Random;

/**
 * @author hhs
 * @since 2021/2/25 16:00
 */
public class RandomUtils {

    private static Random random = new Random();

    public static boolean win(int max) {
        int i = random.nextInt(10);
        return i < max;
    }
}
