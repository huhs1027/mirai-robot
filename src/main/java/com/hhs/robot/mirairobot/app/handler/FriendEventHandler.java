package com.hhs.robot.mirairobot.app.handler;

import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.message.FriendMessageEvent;

/**
 * @author hhs
 * @since 2020/11/25 15:26
 * 好友消息处理
 */
public interface FriendEventHandler {
    /**
     * 处理群组消息
     *
     * @param friendMessageEvent 好友消息事件
     * @return MessageVO 如果是null则不处理
     */
    MessageVO handlerGroupMessage(FriendMessageEvent friendMessageEvent);

    /**
     * 匹配
     *
     * @param friendMessageEvent 消息事件
     * @return true则处理 false不处理
     */
    boolean match(FriendMessageEvent friendMessageEvent);

    /**
     * 返回顺序
     *
     * @return 从小到大先执行
     */
    int sort();
}
