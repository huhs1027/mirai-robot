package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.enums.CardType;
import com.hhs.robot.mirairobot.app.enums.GoldRecordType;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.CardEntity;
import com.hhs.robot.mirairobot.dao.entity.DaGongEntity;
import com.hhs.robot.mirairobot.dao.service.CardService;
import com.hhs.robot.mirairobot.dao.service.DaGongService;
import com.hhs.robot.mirairobot.dao.service.GoldService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author hhs
 * @since 2020/11/25 19:07
 */
@Slf4j
@Component
public class DaGongGroupHandler implements GroupEventHandler {

    @Autowired
    private DaGongService daGongService;

    @Autowired
    private GoldService goldService;

    @Autowired
    private CardService cardService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        long qq = groupMessageEvent.getSender().getId();
        // 查上次打工记录表
        DaGongEntity daGongEntity = daGongService.queryLast(qq);
        // 判断是否可以打工
        if (daGongEntity != null) {
            // 判断是否超过10分钟了
            LocalDateTime lastTime = daGongEntity.getLastTime();
            Duration between = Duration.between(lastTime, LocalDateTime.now());
            long dur = between.abs().toMinutes();
            if (dur < 10) {
                return MessageVO.create().at(groupMessageEvent.getSender())
                        .addText("要钱不要命啊?再休息" + (10 - dur) + "分钟吧");
            }
        }

        // 添加打工记录
        daGongService.dagong(qq);

        // 可以打工
        // 随机金币数
        int gold = new Random().nextInt(2) + 1;
        // 添加金币
        goldService.addGold(qq, gold, GoldRecordType.DAGONG.getKey());
        // 查总金币
        int total = goldService.queryGold(qq);

        MessageVO messageVO = MessageVO.create()
                .addText("加油，打工人！ヽ(°◇° )ノ")
                .at(groupMessageEvent.getSender())
                .addText("\n")
                .addText("您通过辛苦搬砖获得" + gold + "枚金币!");

        // 添加卡片
        int i = new Random().nextInt(100) + 1;
        if (i == 100) {
            // 卡片
            CardEntity chouka = cardService.chouka(qq);
            CardType byKey = CardType.getByKey(chouka.getCardType());

            messageVO.addText("\n")
                    .addText("并在砖头堆里额外发现了[" + byKey.getValue() + "]一张");
        }

        messageVO.addText("\n")
                .addText("当前共有金币" + total + "枚");

        return messageVO;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.equals("打工");
    }

    @Override
    public int sort() {
        return 0;
    }
}
