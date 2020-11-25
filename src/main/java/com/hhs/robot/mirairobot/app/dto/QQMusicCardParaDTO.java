package com.hhs.robot.mirairobot.app.dto;

import lombok.Data;

import java.util.List;

/**
 * @author hhs
 * @since 2020/11/25 13:05
 * 卡片参数DTO
 */
@Data
public class QQMusicCardParaDTO {

    /**
     * 歌曲标题
     */
    private String title;

    /**
     * 歌曲链接id
     */
    private String mid;

    /**
     * 歌曲预览图id
     */
    private String pmid;

    /**
     * 歌手名称
     */
    private List<String> songer;

}
