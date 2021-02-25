package com.hhs.robot.mirairobot.core.dispatcher;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.factory.EventHandlerContainer;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

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

        List<GroupEventCommend> groupEventCommends = null;
        // 指令模式
        if (event.getMessage().contentToString().startsWith(".")) {
            // 判断是否需要处理的群组
            groupEventCommends = EventHandlerContainer.commendGroupMap.get(event.getGroup().getId());
        } else {
            // 非指令模式
            groupEventCommends = EventHandlerContainer.notCommendGroupMap.get(event.getGroup().getId());
        }

        // 判断命令列表
        if (CollectionUtils.isEmpty(groupEventCommends)) {
            return;
        }

        for (GroupEventCommend groupEventHandler : groupEventCommends) {
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
