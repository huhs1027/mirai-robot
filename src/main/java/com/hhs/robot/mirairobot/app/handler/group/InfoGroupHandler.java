package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.enums.CardType;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.CardEntity;
import com.hhs.robot.mirairobot.dao.service.CardService;
import com.hhs.robot.mirairobot.dao.service.GoldService;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hhs
 * @since 2020/11/25 18:52
 */
@Component
public class InfoGroupHandler implements GroupEventHandler {

    @Autowired
    private GoldService goldService;

    @Autowired
    private CardService cardService;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        long qq = groupMessageEvent.getSender().getId();
        // 查金币
        int gold = goldService.queryGold(qq);

        // 查卡片
        List<CardEntity> cardList = cardService.queryunusedCard(qq);
        // 组装卡片
        int baipiao = 0;
        int qiangduo = 0;
        for (CardEntity cardEntity : cardList) {
            // 白嫖卡
            CardType byKey = CardType.getByKey(cardEntity.getCardType());
            if (byKey == CardType.BAIPIAO) {
                baipiao++;
            }
            if (byKey == CardType.QIANGDUO) {
                qiangduo++;
            }
        }

        return MessageVO.create()
                .at(groupMessageEvent.getSender())
                .addText("\n" + "金币" + gold + "\n")
                .addText("白嫖卡" + baipiao + "\n")
                .addText("抢夺卡" + qiangduo + "\n")
                ;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.equals("!详情");
    }

    @Override
    public int sort() {
        return 5;
    }
}
