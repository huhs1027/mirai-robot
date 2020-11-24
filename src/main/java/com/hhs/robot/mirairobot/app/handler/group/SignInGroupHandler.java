package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.dao.entity.SignInEntity;
import com.hhs.robot.mirairobot.dao.mapper.SignInMapper;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/24 20:32
 * 签到消息事件
 */
@Component
public class SignInGroupHandler implements GroupEventHandler {

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public int sort() {
        return 0;
    }

    @Override
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
        } else {
            // 已签到
            return MessageVO.create()
                    .at(groupMessageEvent.getSender())
                    .addText(" 今日已签到!");
        }

        return MessageVO.create()
                .at(groupMessageEvent.getSender())
                .addText(" 签到成功!");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        return groupMessageEvent.getMessage().contentToString().equals("签到");
    }

}
