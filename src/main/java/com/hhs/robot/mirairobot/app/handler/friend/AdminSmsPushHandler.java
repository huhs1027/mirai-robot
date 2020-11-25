package com.hhs.robot.mirairobot.app.handler.friend;

import com.alibaba.fastjson.JSON;
import com.hhs.robot.mirairobot.app.dto.AdminMsgPushDTO;
import com.hhs.robot.mirairobot.app.handler.FriendEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.factory.BotContainer;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.FriendMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author hhs
 * @since 2020/11/25 15:27
 */
@Component
public class AdminSmsPushHandler implements FriendEventHandler {

    @Override
    public MessageVO handlerGroupMessage(FriendMessageEvent friendMessageEvent) {

        String string = friendMessageEvent.getMessage().contentToString();
        AdminMsgPushDTO adminMsgPushDTO = JSON.parseObject(string, AdminMsgPushDTO.class);

        // 收到的是json格式
        Bot bot = friendMessageEvent.getBot();
        Group group = bot.getGroup(adminMsgPushDTO.getGroupId());
        group.sendMessage(adminMsgPushDTO.getMsg());

        return null;
    }

    @Override
    public boolean match(FriendMessageEvent friendMessageEvent) {
        Bot bot = BotContainer.getBot("幼儿园机器人");
        return bot != null && friendMessageEvent.getSender().getId() == 282129720L;
    }

    @Override
    public int sort() {
        return Integer.MIN_VALUE;
    }
}
