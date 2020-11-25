package com.hhs.robot.mirairobot.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hhs
 * @since 2020/11/25 17:49
 */
@Getter
@AllArgsConstructor
public enum CardType {


    QIANGDUO("qiangduo", "抢夺卡", 0),
    BAIPIAO("baipiao", "白嫖卡", 1),
    ;

    private String key;

    private String value;

    private int index;

    public static CardType getByIndex(int index) {
        for (CardType value : CardType.values()) {
            if (value.index == index) {
                return value;
            }
        }

        return null;
    }

    public static CardType getByKey(String key) {
        for (CardType value : CardType.values()) {
            if (value.key == key) {
                return value;
            }
        }

        return null;
    }

}
