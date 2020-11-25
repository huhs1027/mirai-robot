package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/25 17:42
 */
@Data
@TableName("card")
public class CardEntity extends BaseEntity {

    private Long qq;

    private String cardType;

    private Boolean used;

    private LocalDateTime addTime;

    private LocalDateTime usedTime;
}
