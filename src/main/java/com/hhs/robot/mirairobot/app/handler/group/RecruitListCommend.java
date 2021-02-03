package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.RecruitApplyEntity;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitApplyMapper;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 招募
 *
 * @author hhs
 * @since 2021/2/3 10:38
 */
@Component
public class RecruitListCommend implements GroupEventCommend {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private RecruitApplyMapper recruitApplyMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        // .查招募

        // List<String> param = MessageParamUtils.getParam(groupMessageEvent.getMessage().contentToString());

        // 创建招募
        QueryWrapper<RecruitEntity> recruitEntityQueryWrapper = new QueryWrapper<>();
        recruitEntityQueryWrapper.orderByDesc("id");
        Page<RecruitEntity> recruitEntityPage = recruitMapper.selectPage(new Page<>(1, 3), recruitEntityQueryWrapper);

        List<RecruitEntity> records = recruitEntityPage.getRecords();

        if (!CollectionUtils.isEmpty(records)) {
            MessageVO messageVO = MessageVO.create()
                    .addText("最新招募信息如下:\n");
            for (RecruitEntity record : records) {

                QueryWrapper<RecruitApplyEntity> recruitApplyEntityQueryWrapper = new QueryWrapper<>();
                recruitApplyEntityQueryWrapper.eq("recruit_id", record.getId());
                List<RecruitApplyEntity> recruitApplyEntities = recruitApplyMapper.selectList(recruitApplyEntityQueryWrapper);

                messageVO.addText("=============\n")
                        .addText("招募id:").addText(record.getId().toString()).addText("\n")
                        .addText("创建时间:").addText(record.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).addText("\n")
                        .addText("队长:").addText(record.getNick()).addText("\n")
                        .addText("副本:").addText(record.getTitle()).addText("\n")
                        .addText("时间:").addText(record.getTime()).addText("\n")
                        .addText("队员:").addText("\n");

                if (!CollectionUtils.isEmpty(recruitApplyEntities)) {
                    for (RecruitApplyEntity recruitApplyEntity : recruitApplyEntities) {
                        messageVO.addText(recruitApplyEntity.getPosition() + " \t " + recruitApplyEntity.getJob() + " \t " + recruitApplyEntity.getNick() + "\n");
                    }
                }

            }

            return messageVO;
        }

        return null;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.startsWith(".查招募") || string.startsWith(".招募信息");
    }

    @Override
    public int sort() {
        return 3;
    }
}
