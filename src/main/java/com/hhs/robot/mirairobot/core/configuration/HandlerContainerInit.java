package com.hhs.robot.mirairobot.core.configuration;

import com.hhs.robot.mirairobot.app.handler.FriendEventHandler;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
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
        List<GroupEventHandler> beanList = BeanFactory.getBeanList(GroupEventHandler.class);
        // 排序
        beanList.sort(Comparator.comparing(GroupEventHandler::sort));
        for (GroupEventHandler groupEventHandler : beanList) {
            EventHandlerContainer.addGroupHandler(groupEventHandler);
        }


        // 缓存handler
        List<FriendEventHandler> friendEventHandlerList = BeanFactory.getBeanList(FriendEventHandler.class);
        // 排序
        friendEventHandlerList.sort(Comparator.comparing(FriendEventHandler::sort));
        for (FriendEventHandler friendEventHandler : friendEventHandlerList) {
            EventHandlerContainer.addFriendHandler(friendEventHandler);
        }
    }
}
