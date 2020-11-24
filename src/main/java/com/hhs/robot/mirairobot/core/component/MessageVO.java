package com.hhs.robot.mirairobot.core.component;

import lombok.Data;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @author hhs
 * @since 2020/11/24 20:07
 * 处理结束后的消息对象
 */
@Data
public class MessageVO {

    public static MessageVO create() {
        return new MessageVO();
    }

    /**
     * 回复的消息
     */
    private MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

    public MessageVO at(Member member) {
        messageChainBuilder.add(new At(member));
        return this;
    }

    public MessageVO addText(String text) {
        messageChainBuilder.add(text);
        return this;
    }

    public MessageVO add(Message message) {
        messageChainBuilder.add(message);
        return this;
    }

    public MessageChain build() {
        return messageChainBuilder.build();
    }
}
