package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/24 18:15
 */
@Data
@TableName("recruit_apply")
public class RecruitApplyEntity extends BaseEntity {

    /**
     * 招募id
     */
    private Integer recruitId;

    /**
     * 报名人qq
     */
    private Long qq;

    /**
     * 报名人名称
     */
    private String nick;

    /**
     * 职业
     */
    private String job;

    /**
     * 位置
     */
    private String position;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
