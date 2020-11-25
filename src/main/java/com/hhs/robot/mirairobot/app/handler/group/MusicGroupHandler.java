package com.hhs.robot.mirairobot.app.handler.group;

import com.hhs.robot.mirairobot.app.dto.MusicSearchDTO;
import com.hhs.robot.mirairobot.app.external.music.QQMusicProcessor;
import com.hhs.robot.mirairobot.app.handler.GroupEventHandler;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.LightApp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hhs
 * @since 2020/11/25 8:54
 * 没实现... 等去github上看下大佬的写法
 */
@Slf4j
//@Component
public class MusicGroupHandler implements GroupEventHandler {

    @Autowired
    private QQMusicProcessor qqMusicProcessor;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        String string = groupMessageEvent.getMessage().contentToString();
        String[] s = string.split(" ");

        MusicSearchDTO musicSearchDTO = new MusicSearchDTO();
        if (s.length == 3) {
            musicSearchDTO.setSongName(s[1]);
            musicSearchDTO.setSingerName(s[2]);
        } else {
            musicSearchDTO.setSongName(s[1]);
        }

        String s1 = qqMusicProcessor.searchMusic(musicSearchDTO);
        log.info("qq音乐卡片json:{}", s1);
        if (s1 != null) {
            LightApp lightApp = new LightApp(s1);
            return MessageVO.create().add(lightApp);
        }
        return null;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        String content = groupMessageEvent.getMessage().contentToString();
        return content.startsWith("!点歌 ");
    }

    @Override
    public int sort() {
        return 1;
    }
}
