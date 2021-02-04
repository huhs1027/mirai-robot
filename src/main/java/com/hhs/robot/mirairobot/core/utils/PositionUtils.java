package com.hhs.robot.mirairobot.core.utils;

import java.util.ArrayList;

/**
 * @author hhs
 * @since 2021/2/4 10:43
 */
public class PositionUtils {

    public static ArrayList<String> positionList;

    static {
        positionList = new ArrayList<>();
        positionList.add("MT");
        positionList.add("ST");
        positionList.add("H1");
        positionList.add("H2");
        positionList.add("D1");
        positionList.add("D2");
        positionList.add("D3");
        positionList.add("D4");
    }

    public static boolean checkPosition(String pos) {
        return positionList.contains(pos);
    }
}
