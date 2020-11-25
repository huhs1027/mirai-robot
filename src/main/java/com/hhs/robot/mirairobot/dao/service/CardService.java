package com.hhs.robot.mirairobot.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.enums.CardType;
import com.hhs.robot.mirairobot.dao.entity.CardEntity;
import com.hhs.robot.mirairobot.dao.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author hhs
 * @since 2020/11/25 18:08
 */
@Service
public class CardService {

    @Autowired
    private CardMapper cardMapper;

    /**
     * 抽卡
     *
     * @param qq qq
     */
    public CardEntity chouka(long qq) {

        // 抽卡
        CardType cardType = CardType.getByIndex(new Random().nextInt(CardType.values().length));
        // 加到记录里
        CardEntity cardEntity = new CardEntity();
        cardEntity.setQq(qq);
        cardEntity.setCardType(cardType.getKey());
        cardEntity.setUsed(false);
        cardEntity.setAddTime(LocalDateTime.now());

        cardMapper.insert(cardEntity);

        return cardEntity;
    }

    /**
     * 查卡片
     *
     * @param qq qq
     * @return 卡片列表
     */
    public List<CardEntity> queryunusedCard(long qq) {
        QueryWrapper<CardEntity> cardEntityQueryWrapper = new QueryWrapper<>();
        cardEntityQueryWrapper.eq("qq", qq);
        cardEntityQueryWrapper.eq("used", false);
        return cardMapper.selectList(cardEntityQueryWrapper);
    }
}
