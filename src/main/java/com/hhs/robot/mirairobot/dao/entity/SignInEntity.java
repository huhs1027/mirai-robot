package com.hhs.robot.mirairobot.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/24 21:57
 */
@Data
@TableName("sign_in")
public class SignInEntity extends BaseEntity {

    /**
     * qq
     */
    private Long qq;

    /**
     * 签到时间
     */
    private LocalDateTime signDay;

    /**
     * 签到日期
     */
    private LocalDate signDate;

}
