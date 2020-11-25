package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.enums.GoldRecordType;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.SignInEntity;
import com.hhs.robot.mirairobot.dao.mapper.SignInMapper;
import com.hhs.robot.mirairobot.dao.service.GoldService;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author hhs
 * @since 2020/11/24 20:32
 * 签到消息事件
 */
@Component
public class SignInGroupHandler implements GroupEventHandler {

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private GoldService goldService;

    @Override
    public int sort() {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        long qq = groupMessageEvent.getSender().getId();

        // 查库是否签到过
        QueryWrapper objectWrapper = new QueryWrapper<SignInEntity>()
                .eq("qq", qq)
                .eq("sign_date", LocalDate.now());
        SignInEntity signInEntity = signInMapper.selectOne(objectWrapper);

        if (signInEntity == null) {
            // 签到
            SignInEntity save = new SignInEntity();
            save.setQq(qq);
            save.setSignDay(LocalDateTime.now());
            save.setSignDate(LocalDate.now());

            signInMapper.insert(save);

            // 随机获取金币
            int randomGold = randomGold();
            // 入库
            goldService.addGold(qq, randomGold, GoldRecordType.SIGN.getKey());
            // 查总金币
            int total = goldService.queryGold(qq);

            return MessageVO.create()
                    .at(groupMessageEvent.getSender())
                    .addText(" 签到成功!获得" + randomGold + "枚金币" + "\n")
                    .addText("当前总金币" + total + "枚")
                    ;
        } else {
            // 已签到
            return MessageVO.create()
                    .at(groupMessageEvent.getSender())
                    .addText(" 今日已签到!");
        }
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        return groupMessageEvent.getMessage().contentToString().equals("签到");
    }

    private Random random = new Random();

    private int randomGold() {
        return (int) (10 + random.nextDouble() * (20 - 10 + 1));
    }

}
