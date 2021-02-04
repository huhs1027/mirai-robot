package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hhs
 * @since 2020/11/24 18:15
 */
@Data
@TableName("robot_config")
public class RobotConfigEntity extends BaseEntity {

    /**
     * 机器人名称
     */
    private String name;

    /**
     * 机器人qq
     */
    private Long qq;

    /**
     * 机器人密码
     */
    private String password;

    /**
     * 机器人支持的群 , 分割
     */
    @TableField("`group`")
    private String group;
}
