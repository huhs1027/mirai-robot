package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/25 17:42
 */
@Data
@TableName("dagong")
public class DaGongEntity extends BaseEntity {

    private Long qq;

    private LocalDateTime lastTime;
}
