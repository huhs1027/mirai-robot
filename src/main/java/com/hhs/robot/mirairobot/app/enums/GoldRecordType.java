package com.hhs.robot.mirairobot.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hhs
 * @since 2020/11/25 18:11
 */
@Getter
@AllArgsConstructor
public enum GoldRecordType {

    SIGN("sign", "签到奖励"),
    CHOUKA("chouka", "抽卡消耗"),
    DAGONG("dagong", "打工赚取"),

    ;

    private String key;

    private String value;

}
