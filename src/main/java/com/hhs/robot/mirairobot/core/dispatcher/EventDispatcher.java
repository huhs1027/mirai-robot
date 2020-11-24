package com.hhs.robot.mirairobot.core.dispatcher;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.MessageEvent;

/**
 * @author hhs
 * @since 2020/11/24 19:25
 * 事件分配器
 */
@Slf4j
public class EventDispatcher extends SimpleListenerHost {

    @EventHandler
    public ListeningStatus handle(MessageEvent event) {
        log.info("监听到消息事件:{}", event.getMessage().contentToString());

        return ListeningStatus.LISTENING;
    }

}
