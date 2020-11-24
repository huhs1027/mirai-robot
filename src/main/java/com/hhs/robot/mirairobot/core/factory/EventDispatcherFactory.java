package com.hhs.robot.mirairobot.core.factory;

import com.hhs.robot.mirairobot.core.dispatcher.EventDispatcher;
import net.mamoe.mirai.event.SimpleListenerHost;

/**
 * @author hhs
 * @since 2020/11/24 19:29
 */
public class EventDispatcherFactory {

    public static SimpleListenerHost getDefaultDispatcher() {
        return new EventDispatcher();
    }

}
