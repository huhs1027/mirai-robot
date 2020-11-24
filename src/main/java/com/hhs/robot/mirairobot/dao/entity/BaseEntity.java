package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author hhs
 * @since 2020/11/24 22:08
 */
@Data
public abstract class BaseEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

}
