package com.hhs.robot.mirairobot.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 夸夸台词
 *
 * @author hhs
 * @since 2021/2/4 11:16
 */
public class KKUtils {

    public static List<String> kkList;

    static {
        kkList = new ArrayList<>();

        kkList.add("嘿嘿……你那踏雪疾奔的身姿,真是好棒啊……");
        kkList.add("嘿嘿……你眼底闪耀的光芒……真的好棒!");
        kkList.add("冒险者的身影简直……太棒了!");
        kkList.add("你的肉体,还是那么销魂!太棒了!");
        kkList.add("太棒了!随时欢迎!毕竟你是最棒的啊!");
    }

    public static String randomKK() {
        int i = new Random().nextInt(kkList.size());
        return kkList.get(i);
    }

}
