package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.MessageParamUtils;
import com.hhs.robot.mirairobot.dao.entity.RecruitApplyEntity;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitApplyMapper;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 招募
 *
 * @author hhs
 * @since 2021/2/3 10:38
 */
@Component
public class RecruitApplyCommend implements GroupEventCommend {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private RecruitApplyMapper recruitApplyMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        // .进组 1 机工 D3

        List<String> param = MessageParamUtils.getParam(groupMessageEvent.getMessage().contentToString());
        if (param.size() != 3) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSender().getNick()).addText("]").addText(" ").addText("格式不正确")
                    .addText("\n")
                    .addText("格式: .进组 招募id 职业 位置")
                    ;
        }

        // 查招募
        QueryWrapper<RecruitEntity> recruitEntityQueryWrapper = new QueryWrapper<>();
        recruitEntityQueryWrapper.eq("id", param.get(0));
        RecruitEntity recruitEntity1 = recruitMapper.selectOne(recruitEntityQueryWrapper);
        if (recruitEntity1 == null) {
            return MessageVO.create().addText("[").addText(groupMessageEvent.getSender().getNick()).addText("]").addText("招募不存在");
        }

        // 创建队员
        RecruitApplyEntity recruitEntity = new RecruitApplyEntity();
        recruitEntity.setRecruitId(recruitEntity1.getId());
        recruitEntity.setQq(groupMessageEvent.getSender().getId());
        recruitEntity.setNick(groupMessageEvent.getSenderName());
        recruitEntity.setJob(param.get(1));
        recruitEntity.setPosition(param.get(2));
        recruitEntity.setCreateTime(LocalDateTime.now());

        recruitApplyMapper.insert(recruitEntity);

        return MessageVO.create().addText("[").addText(groupMessageEvent.getSender().getNick()).addText("]").addText(" ").addText("报名成功");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.startsWith(".报名") || string.startsWith(".进组");
    }

    @Override
    public int sort() {
        return 2;
    }
}
