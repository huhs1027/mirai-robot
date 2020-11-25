package com.hhs.robot.mirairobot.app.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author hhs
 * @since 2020/11/25 13:27
 * qq音乐卡片分享DTO
 */
@Data
public class QQMusicCardDTO {

    private String app;

    private JSONObject config;

    private String desc;

    private JSONObject extra;

    private JSONObject meta;

    private String prompt;

    private String ver;

    private String view;

}
