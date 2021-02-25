package com.hhs.robot.mirairobot.core.configuration;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.annon.Commend;
import com.hhs.robot.mirairobot.core.factory.BeanFactory;
import com.hhs.robot.mirairobot.core.factory.EventHandlerContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 20:42
 */
@Slf4j
@Order(1)
@Configuration
public class CommendContainerInit implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化handler");
        // 缓存handler
        List<GroupEventCommend> beanList = BeanFactory.getBeanList(GroupEventCommend.class);
        // 排序
        beanList.sort(Comparator.comparing(GroupEventCommend::sort));

        // 临时存放所有群组的命令
        ArrayList<GroupEventCommend> commendAllGroup = new ArrayList<>();
        ArrayList<GroupEventCommend> notCommendAllGroup = new ArrayList<>();

        for (GroupEventCommend groupEventHandler : beanList) {
            // 获取群组
            Commend annotation = groupEventHandler.getClass().getAnnotation(Commend.class);
            if (annotation != null) {
                if (annotation.allGroup()) {
                    if (annotation.commend()) {
                        commendAllGroup.add(groupEventHandler);
                    } else {
                        notCommendAllGroup.add(groupEventHandler);
                    }

                } else {
                    long[] group = annotation.group();
                    if (group.length > 0) {
                        for (long qq : group) {
                            if (annotation.commend()) {
                                EventHandlerContainer.commendGroupMap.computeIfAbsent(qq, value -> new ArrayList<>()).add(groupEventHandler);
                            } else {
                                EventHandlerContainer.notCommendGroupMap.computeIfAbsent(qq, value -> new ArrayList<>()).add(groupEventHandler);
                            }

                        }
                    }
                }
            }
        }

        // 给map添加所有群组
        EventHandlerContainer.commendGroupMap.forEach((qq, list) -> list.addAll(commendAllGroup));
        EventHandlerContainer.notCommendGroupMap.forEach((qq, list) -> list.addAll(notCommendAllGroup));

    }
}
