package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/24 18:15
 */
@Data
@TableName("recruit")
public class RecruitEntity extends BaseEntity {

    /**
     * 招募人qq
     */
    private Long qq;

    /**
     * 招募人名称
     */
    private String nick;

    /**
     * 招募标题
     */
    private String title;

    /**
     * 攻略时间
     */
    private String time;

    /**
     * 是否关闭 Y N
     */
    private String close;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
