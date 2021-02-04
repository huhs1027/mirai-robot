package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.MessageParamUtils;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 招募
 *
 * @author hhs
 * @since 2021/2/3 10:38
 */
@Component
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

        if (recruitEntity.getClose().equals("Y")) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("已关闭");
        }

        recruitEntity.setClose("Y");
        recruitMapper.updateById(recruitEntity);

        return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("英雄,期待下次冒险!");
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
