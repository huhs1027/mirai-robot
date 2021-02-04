package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.KKUtils;
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

        return MessageVO.create()
                .addText(KKUtils.randomKK() + "\n")
                .addText(".查招募 (查看最新的3个招募信息) \n")
                .addText(".开组 标题 时间 (开启一个招募) \n")
                .addText(".关闭 招募id (关闭自己的招募) \n")
                .addText(".进组 招募id 职业 位置 (选择一个招募进队) \n")
                ;
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
