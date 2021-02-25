package com.hhs.robot.mirairobot.core.annon;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件
 *
 * @author hhs
 * @since 2021/2/25 15:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Commend {

    /**
     * 支持所有组
     *
     * @return true是 false否, false需要配置指定的群号
     */
    boolean allGroup() default false;

    /**
     * 支持的群号
     * 724988240 测试群
     * 147392528 ff14
     * 467236828 古剑
     */
    long[] group() default {};

    /**
     * 指令模式
     *
     * @return
     */
    boolean commend() default true;
}
