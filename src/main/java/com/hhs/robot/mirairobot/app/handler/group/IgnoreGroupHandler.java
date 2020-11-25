package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author hhs
 * @since 2020/11/24 20:32
 * 未识别消息事件
 */
@Component
public class IgnoreGroupHandler implements GroupEventHandler {

    @Override
    public int sort() {
        return Integer.MAX_VALUE;
    }

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        return MessageVO.create()
                .at(groupMessageEvent.getSender())
                .addText(" 还在学习中~");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        if (string.contains("@" + groupMessageEvent.getBot().getNick())) {
            return true;
        }
        return false;
    }

}
