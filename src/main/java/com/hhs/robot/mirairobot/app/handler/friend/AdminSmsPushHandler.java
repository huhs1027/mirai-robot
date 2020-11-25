package com.hhs.robot.mirairobot.app.handler.friend;

import com.hhs.robot.mirairobot.app.handler.FriendEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author hhs
 * @since 2020/11/25 15:27
 */
@Component
public class AdminSmsPushHandler implements FriendEventHandler {

    @Override
    public MessageVO handlerGroupMessage(FriendMessageEvent friendMessageEvent) {

        /*String string = friendMessageEvent.getMessage().contentToString();
        AdminMsgPushDTO adminMsgPushDTO = JSON.parseObject(string, AdminMsgPushDTO.class);

        // 收到的是json格式
        Bot bot = friendMessageEvent.getBot();
        Group group = bot.getGroup(adminMsgPushDTO.getGroupId());
        group.sendMessage(adminMsgPushDTO.getMsg());

        return null;*/
        try {
            File file = ResourceUtils.getFile("classpath:img/123.jpg");
            if (file.exists()) {
                final Image image = friendMessageEvent.getSender().uploadImage(file);
                String imageId = image.getImageId();
                //String imageId = image.getImageId();
//                return MessageVO.create()
//                        .add(image)
//                        .addText(imageId)
//                        ;
                Future<MessageReceipt<Contact>> messageReceiptFuture = friendMessageEvent.getSender().sendMessageAsync(image);
                messageReceiptFuture.get();
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean match(FriendMessageEvent friendMessageEvent) {
        /*Bot bot = BotContainer.getBot("幼儿园机器人");
        return bot != null && friendMessageEvent.getSender().getId() == 282129720L;*/
        return friendMessageEvent.getMessage().contentToString().equals("!图来");
    }

    @Override
    public int sort() {
        return Integer.MIN_VALUE;
    }
}
