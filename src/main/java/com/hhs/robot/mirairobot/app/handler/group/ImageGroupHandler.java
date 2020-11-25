package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author hhs
 * @since 2020/11/25 22:58
 */
@Component
public class ImageGroupHandler implements GroupEventHandler {
    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        try {
            File file = ResourceUtils.getFile("classpath:img/banner.jpg");
            if (file.exists()) {
                final Image image = groupMessageEvent.getGroup().uploadImage(file);
                String imageId = image.getImageId();
                //String imageId = image.getImageId();
                return MessageVO.create()
                        .add(image)
                        .addText(imageId)
                        ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String string = groupMessageEvent.getMessage().contentToString();
        return string.equals("!图来");
    }

    @Override
    public int sort() {
        return 5;
    }
}
