package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.PositionUtils;
import com.hhs.robot.mirairobot.dao.entity.RecruitApplyEntity;
import com.hhs.robot.mirairobot.dao.entity.RecruitEntity;
import com.hhs.robot.mirairobot.dao.mapper.RecruitApplyMapper;
import com.hhs.robot.mirairobot.dao.mapper.RecruitMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        recruitEntityQueryWrapper.eq("close", "N");
        recruitEntityQueryWrapper.orderByDesc("id");
        Page<RecruitEntity> recruitEntityPage = recruitMapper.selectPage(new Page<>(1, 3), recruitEntityQueryWrapper);

        List<RecruitEntity> records = recruitEntityPage.getRecords();

        MessageVO messageVO = MessageVO.create()
                .addText("挚友,要一起去冒险吗?\n");

        if (!CollectionUtils.isEmpty(records)) {
            for (RecruitEntity record : records) {

                QueryWrapper<RecruitApplyEntity> recruitApplyEntityQueryWrapper = new QueryWrapper<>();
                recruitApplyEntityQueryWrapper.eq("recruit_id", record.getId());
                List<RecruitApplyEntity> recruitApplyEntities = recruitApplyMapper.selectList(recruitApplyEntityQueryWrapper);
                messageVO.addText("=============\n")
                        .addText("招募id: ").addText(record.getId().toString()).addText("\n")
                        .addText("队长: ").addText(record.getNick()).addText("\n")
                        .addText("副本: ").addText(record.getTitle()).addText("\n")
                        .addText("时间: ").addText(record.getTime()).addText("\n")
                        .addText("队员: ").addText("\n");

                // 转map
                Map<String, RecruitApplyEntity> posMap = recruitApplyEntities.stream().collect(Collectors.toMap(RecruitApplyEntity::getPosition, v -> v));

                for (String position : PositionUtils.positionList) {
                    RecruitApplyEntity recruitApplyEntity = posMap.get(position);
                    String job = "";
                    String nick = "";
                    if (recruitApplyEntity != null) {
                        job = recruitApplyEntity.getJob();
                        nick = recruitApplyEntity.getNick();
                    }
                    messageVO.addText(position + " \t " + job + " \t " + nick + "\n");
                }
            }
        }

        return messageVO;
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
