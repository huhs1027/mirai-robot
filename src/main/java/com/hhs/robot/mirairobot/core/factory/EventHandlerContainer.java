package com.hhs.robot.mirairobot.core.factory;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 20:40
 * 处理器容器,缓存
 */
public class EventHandlerContainer {

    private static List<GroupEventCommend> groupEventHandlers = new ArrayList<>();

    // 群组消息处理器列表
    public static List<GroupEventCommend> getGroupEventHandlers() {
        return groupEventHandlers;
    }

    public static void addGroupHandler(GroupEventCommend groupEventHandler) {
        if (groupEventHandler != null) {
            groupEventHandlers.add(groupEventHandler);
        }
    }

}
