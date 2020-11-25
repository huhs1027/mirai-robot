package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hhs
 * @since 2020/11/25 17:42
 */
@Data
@TableName("gold")
public class GoldEntity extends BaseEntity {

    private Long qq;

    private Integer gold;
}
