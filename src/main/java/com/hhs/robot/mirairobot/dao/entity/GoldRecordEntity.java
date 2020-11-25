package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/25 17:42
 */
@Data
@TableName("gold_record")
public class GoldRecordEntity extends BaseEntity {

    private Long qq;

    private LocalDateTime dayTime;

    private String recordType;

    private Integer num;

}
