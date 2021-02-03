package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author hhs
 * @since 2021/2/3 10:15
 */
@Component
public class HelpCommend implements GroupEventCommend {

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        return MessageVO.create().addText("help");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.startsWith(".help");
    }

    @Override
    public int sort() {
        return Integer.MAX_VALUE;
    }
}
