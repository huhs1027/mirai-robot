package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.annon.Commend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.MessageParamUtils;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 招募
 *
 * @author hhs
 * @since 2021/2/3 10:38
 */
@Commend(group = { 147392528, 724988240 })
public class RecruitCloseCommend implements GroupEventCommend {

    @Autowired
    private RecruitMapper recruitMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        // .关闭 1

        List<String> param = MessageParamUtils.getParam(groupMessageEvent.getMessage().contentToString());
        if (param.size() != 1) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("格式不正确")
                    .addText("\n")
                    .addText("格式: .关闭 招募id");
        }

        // 查招募
        RecruitEntity recruitEntity = recruitMapper.selectById(param.get(0));
        if (recruitEntity == null) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("招募不存在");
        }

        if (!recruitEntity.getQq().equals(groupMessageEvent.getSender().getId())) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("只能关闭自己的招募");
        }

        recruitEntity.setClose("Y");
        recruitMapper.updateById(recruitEntity);

        return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText("\n")
                .addText("冒险结束了?\n赶快来我房间里暖暖身子……\n为你准备了伊修加德奶茶……");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.startsWith(".关闭");
    }

    @Override
    public int sort() {
        return 1;
    }
}
