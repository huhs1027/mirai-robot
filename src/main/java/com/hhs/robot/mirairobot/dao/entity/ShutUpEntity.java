package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/24 18:15
 */
@Data
@TableName("shut_up")
public class ShutUpEntity extends BaseEntity {

    /**
     * 机器人qq
     */
    private Long botQq;

    /**
     * 机器人密码
     */
    private Long groupQq;

    /**
     * 过期时间
     */
    private LocalDateTime expire;

}
