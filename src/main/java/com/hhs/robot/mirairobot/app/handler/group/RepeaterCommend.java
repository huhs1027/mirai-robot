package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.annon.Commend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.RandomUtils;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.ArrayList;

/**
 * @author hhs
 * @since 2021/2/25 15:57
 */
@Commend(group = { 467236828 }, commend = false)
public class RepeaterCommend implements GroupEventCommend {

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        // 随机 1/3条重复1条
        if (RandomUtils.win(3)) {
            return new MessageVO().add(groupMessageEvent.getMessage());
        }
        return null;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        // 过滤列表
        ArrayList<Long> filterList = new ArrayList<>();
        // 图图机器人
        filterList.add(2854200117L);

        boolean robotContains = groupMessageEvent.getMessage().contentToString().contains("at:2854200117");

        return !filterList.contains(groupMessageEvent.getSender().getId()) && !robotContains;
    }

    @Override
    public int sort() {
        return Integer.MAX_VALUE;
    }
}
