package com.hhs.robot.mirairobot.core.factory;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhs
 * @since 2020/11/24 20:40
 * 处理器容器,缓存
 */
public class EventHandlerContainer {

    /**
     * 指令群组map, key=qq群好, value=支持的指令
     */
    public static Map<Long, List<GroupEventCommend>> commendGroupMap = new HashMap<>();

    /**
     * 非指令
     */
    public static Map<Long, List<GroupEventCommend>> notCommendGroupMap = new HashMap<>();

}
