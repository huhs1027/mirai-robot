package com.hhs.robot.mirairobot.core.factory;

import net.mamoe.mirai.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhs
 * @since 2020/11/25 15:20
 * 机器人容器
 */
public class BotContainer {

    private static List<Bot> botContainer = new ArrayList<>();

    // 机器人列表
    public static List<Bot> getBotContainer() {
        return botContainer;
    }

    public static void addGroupHandler(Bot bot) {
        if (bot != null) {
            botContainer.add(bot);
        }
    }

    /**
     * 通过机器人名字获取机器人
     *
     * @param botName 机器人名字
     * @return Bot
     */
    public static Bot getBot(String botName) {
        for (Bot bot : botContainer) {
            if (bot.getNick().equals(botName)) {
                return bot;
            }
        }

        return null;
    }

}
