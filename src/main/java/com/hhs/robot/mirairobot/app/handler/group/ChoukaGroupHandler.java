package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.enums.CardType;
import com.hhs.robot.mirairobot.app.enums.GoldRecordType;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.CardEntity;
import com.hhs.robot.mirairobot.dao.service.CardService;
import com.hhs.robot.mirairobot.dao.service.GoldService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hhs
 * @since 2020/11/25 17:46
 */
@Slf4j
@Component
public class ChoukaGroupHandler implements GroupEventHandler {

    @Autowired
    private CardService cardService;

    @Autowired
    private GoldService goldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        long qq = groupMessageEvent.getSender().getId();

        // 判断金币
        int num = goldService.queryGold(qq);
        if (num < 10) {
            // 金币不足
            // 已签到
            return MessageVO.create()
                    .at(groupMessageEvent.getSender())
                    .addText(" 金币不足10枚!");
        } else {
            // 抽卡
            CardEntity chouka = cardService.chouka(qq);
            // 金币-10
            goldService.addGold(qq, -10, GoldRecordType.CHOUKA.getKey());

            CardType byKey = CardType.getByKey(chouka.getCardType());

            return MessageVO.create()
                    .at(groupMessageEvent.getSender())
                    .addText(" 消耗金币10枚! 抽到" + byKey.getValue());
        }

    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        if (string.equals("!抽卡")) {
            return true;
        }
        return false;
    }

    @Override
    public int sort() {
        return 2;
    }
}
