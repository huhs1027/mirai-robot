package com.hhs.robot.mirairobot.core.configuration;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.factory.BeanFactory;
import com.hhs.robot.mirairobot.core.factory.EventHandlerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Comparator;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 20:42
 */
@Slf4j
@Order(1)
@Configuration
public class HandlerContainerInit implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化handler");
        // 缓存handler
        List<GroupEventCommend> beanList = BeanFactory.getBeanList(GroupEventCommend.class);
        // 排序
        beanList.sort(Comparator.comparing(GroupEventCommend::sort));
        for (GroupEventCommend groupEventHandler : beanList) {
            EventHandlerContainer.addGroupHandler(groupEventHandler);
        }

    }
}
