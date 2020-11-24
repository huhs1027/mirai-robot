package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author hhs
 * @since 2020/11/24 20:32
 * 签到消息事件
 */
@Component
public class SignInGroupHandler implements GroupEventHandler {

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        MessageVO messageVO = MessageVO.create();

        messageVO.at(groupMessageEvent.getSender())
                .addText(" 签到成功!");

        return messageVO;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        return groupMessageEvent.getMessage().contentToString().contains("签到");
    }

}
