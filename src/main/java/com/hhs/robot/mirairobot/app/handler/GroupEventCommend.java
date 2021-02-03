package com.hhs.robot.mirairobot.app.handler;

import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @author hhs
 * @since 2020/11/24 20:25
 * 群消息事件处理器
 */
public interface GroupEventCommend {

    /**
     * 处理群组消息
     *
     * @param groupMessageEvent 群组消息事件
     * @return MessageVO 如果是null则不处理
     */
    MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent);

    /**
     * 匹配
     *
     * @param groupMessageEvent 消息事件
     * @return true则处理 false不处理
     */
    boolean match(GroupMessageEvent groupMessageEvent);

    /**
     * 返回顺序
     *
     * @return 从小到大先执行
     */
    int sort();

}
