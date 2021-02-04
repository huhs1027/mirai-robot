package com.hhs.robot.mirairobot.core.dispatcher;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.factory.BotContainer;
import com.hhs.robot.mirairobot.core.factory.EventHandlerContainer;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author hhs
 * @since 2020/11/24 19:25
 * 事件分配器
 */
@Slf4j
public class EventDispatcher extends SimpleListenerHost {

    /**
     * 群消息
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void groupHandler(@NotNull GroupMessageEvent event) {
        // 判断机器人是否符合群
        boolean checkBotGroup = BotContainer.checkBotGroup(event.getBot().getId(), event.getSender().getGroup().getId());

        // 匹配指令
        if (checkBotGroup && event.getMessage().contentToString().startsWith(".")) {
            List<GroupEventCommend> groupEventHandlers = EventHandlerContainer.getGroupEventHandlers();
            for (GroupEventCommend groupEventHandler : groupEventHandlers) {
                if (groupEventHandler.match(event)) {
                    MessageVO messageVO = groupEventHandler.handlerGroupMessage(event);
                    if (messageVO != null) {
                        event.getGroup().sendMessage(messageVO.build());
                    }
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
