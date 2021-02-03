package com.hhs.robot.mirairobot.core.configuration;

import com.alibaba.fastjson.JSON;
import com.hhs.robot.mirairobot.core.factory.BotContainer;
import com.hhs.robot.mirairobot.core.factory.EventDispatcherFactory;
import com.hhs.robot.mirairobot.dao.entity.RobotConfigEntity;
import com.hhs.robot.mirairobot.dao.mapper.RobotConfigMapper;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
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

        ArrayList<Runnable> runList = new ArrayList<>();

        // 注册
        for (RobotConfigEntity robotConfigEntity : robotConfigEntityList) {
            Runnable runnable = () -> {
                try {
                    File file = ResourceUtils.getFile("classpath:devices/" + robotConfigEntity.getQq() + ".json");
                    Bot bot = BotFactory.INSTANCE.newBot(robotConfigEntity.getQq(), robotConfigEntity.getPassword(), new BotConfiguration() {
                        {
                            fileBasedDeviceInfo(file.getPath());
                            //setBotLoggerSupplier(bot -> new RobotLog(String.valueOf(bot.getId())));
                            //setNetworkLoggerSupplier(bot -> new RobotLog(bot.getId() + "-network"));
                            setProtocol(MiraiProtocol.ANDROID_PHONE);
                        }
                    });
                    // 加入容器
                    BotContainer.addGroupHandler(bot);
                    // 登录
                    bot.login();
                    // 注册监听事件
                    bot.getEventChannel().registerListenerHost(EventDispatcherFactory.getDefaultDispatcher());
                    log.info(robotConfigEntity.getName() + "注册完成!");
                    // 阻塞等待重连
                    bot.join();
                } catch (Exception e) {
                    log.error("robot启动失败", e);
                }
            };

            runList.add(runnable);
        }

        for (Runnable runnable : runList) {
            runnable.run();
        }
    }
}
