package com.hhs.robot.mirairobot.core.dispatcher;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.factory.EventHandlerContainer;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.TempMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 19:25
 * 事件分配器
 */
@Slf4j
public class EventDispatcher extends SimpleListenerHost {

    @EventHandler
    public ListeningStatus handle(MessageEvent event) {
        // 群组
        if (event instanceof GroupMessageEvent) {
            GroupMessageEvent groupMessageEvent = (GroupMessageEvent) event;
            log.info("群消息:[{}({})]-[{}({})]-[{}]"
                    , groupMessageEvent.getGroup().getName()
                    , groupMessageEvent.getGroup().getId()
                    , groupMessageEvent.getSender().getNick()
                    , groupMessageEvent.getSender().getId()
                    , groupMessageEvent.getMessage().contentToString());

            this.handleGroupEvent((GroupMessageEvent) event);
        }
        // 好友
        else if (event instanceof FriendMessageEvent) {

        }
        // 临时
        else if (event instanceof TempMessageEvent) {

        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 处理群消息
     *
     * @param event 群消息事件
     */
    private void handleGroupEvent(GroupMessageEvent event) {
        List<GroupEventHandler> groupEventHandlers = EventHandlerContainer.getGroupEventHandlers();
        for (GroupEventHandler groupEventHandler : groupEventHandlers) {
            if (groupEventHandler.match(event)) {
                MessageVO messageVO = groupEventHandler.handlerGroupMessage(event);
                if (messageVO != null) {
                    event.getGroup().sendMessage(messageVO.build());
                }
            }
        }
    }


    //处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        throw new RuntimeException("在事件处理中发生异常", exception);
    }

}
