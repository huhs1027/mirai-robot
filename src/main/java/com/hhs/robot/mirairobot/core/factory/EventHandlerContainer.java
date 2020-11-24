package com.hhs.robot.mirairobot.core.factory;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 20:40
 * 处理器容器,缓存
 */
public class EventHandlerContainer {

    private static List<GroupEventHandler> groupEventHandlers = new ArrayList<>();

    // 群组消息处理器列表
    public static List<GroupEventHandler> getGroupEventHandlers() {
        return groupEventHandlers;
    }

    public static void addGroupHandler(GroupEventHandler groupEventHandler) {
        if (groupEventHandler != null) {
            groupEventHandlers.add(groupEventHandler);
        }
    }

}
