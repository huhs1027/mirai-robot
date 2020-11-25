package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author hhs
 * @since 2020/11/25 18:46
 */
@Slf4j
@Component
public class HelpGroupHandler implements GroupEventHandler {
    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        return MessageVO.create()
                .at(groupMessageEvent.getSender())
                .addText("\n")
                .addText("!签到 每天一次,奖励10~20枚金币" + "\n")
                .addText("!抽卡 每次消耗10金币" + "\n")
                .addText("!详情 返回金币和卡片信息" + "\n")
                .addText("!打工 cd10分钟 每次获取1~2枚金币 有小概率获得卡片" + "\n")
                .addText("卡片使用功能后续完善")
                ;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.equals("!help");
    }

    @Override
    public int sort() {
        return 4;
    }
}
