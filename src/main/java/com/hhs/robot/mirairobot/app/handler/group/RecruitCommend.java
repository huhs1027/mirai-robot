package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.MessageParamUtils;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * 招募
 *
 * @author hhs
 * @since 2021/2/3 10:38
 */
@Component
public class RecruitCommend implements GroupEventCommend {

    @Autowired
    private RecruitMapper recruitMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        // .开组 希瓦 晚上8点半

        List<String> param = MessageParamUtils.getParam(groupMessageEvent.getMessage().contentToString());
        if (param.size() != 2) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("格式不正确")
                    .addText("\n")
                    .addText("格式: .开组 标题 时间")
                    ;
        }

        // 创建招募
        RecruitEntity recruitEntity = new RecruitEntity();
        recruitEntity.setQq(groupMessageEvent.getSender().getId());
        recruitEntity.setNick(groupMessageEvent.getSender().getNick());
        recruitEntity.setTitle(param.get(0));
        recruitEntity.setTime(param.get(1));
        recruitEntity.setCreateDate(LocalDate.now());
        recruitMapper.insert(recruitEntity);

        return MessageVO.create().addText("[").addText(groupMessageEvent.getSenderName()).addText("]").addText(" ").addText("开组成功")
                .addText("\n")
                .addText("招募id:").addText(recruitEntity.getId() + "")
                ;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.startsWith(".开招募") || string.startsWith(".开组");
    }

    @Override
    public int sort() {
        return 1;
    }
}
