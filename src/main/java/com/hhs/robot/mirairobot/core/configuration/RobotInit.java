package com.hhs.robot.mirairobot.core.configuration;

import com.alibaba.fastjson.JSON;
import com.hhs.robot.mirairobot.core.component.RobotLog;
import com.hhs.robot.mirairobot.core.factory.EventDispatcherFactory;
import com.hhs.robot.mirairobot.dao.entity.RobotConfigEntity;
import com.hhs.robot.mirairobot.dao.mapper.RobotConfigMapper;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 17:58
 * 机器人注册
 */
@Slf4j
@Order(2)
@Configuration
public class RobotInit implements CommandLineRunner {

    @Autowired
    private RobotConfigMapper robotConfigMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查库获取机器人信息
        List<RobotConfigEntity> robotConfigEntityList = robotConfigMapper.selectList(null);
        log.info("查询到的机器人信息:{}", JSON.toJSONString(robotConfigEntityList));

        // 注册
        for (RobotConfigEntity robotConfigEntity : robotConfigEntityList) {
            File file = ResourceUtils.getFile("classpath:devices/" + robotConfigEntity.getQq() + ".json");
            Bot bot = BotFactoryJvm.newBot(robotConfigEntity.getQq(), robotConfigEntity.getPassword(), new BotConfiguration() {
                {
                    fileBasedDeviceInfo(file.getPath());
                    setBotLoggerSupplier(bot -> new RobotLog(String.valueOf(bot.getId())));
                    setNetworkLoggerSupplier(bot -> new RobotLog(bot.getId() + "-network"));
                    setProtocol(MiraiProtocol.ANDROID_PAD);
                }
            });
            // 登录
            bot.login();
            // 注册监听事件
            Events.registerEvents(bot, EventDispatcherFactory.getDefaultDispatcher());
            log.info(robotConfigEntity.getName() + "注册完成!");
        }
    }
}
