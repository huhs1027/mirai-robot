package com.hhs.robot.mirairobot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hhs
 * @date 2020-11-24
 * demo : https://github.com/project-mirai/mirai-demos/blob/master/mirai-demo-java/src/main/java/demo/BlockingTest.java
 */
@SpringBootApplication
@MapperScan("com.hhs.robot.mirairobot.dao.mapper")
public class MiraiRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiRobotApplication.class, args);
    }

}
