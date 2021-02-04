package com.hhs.robot.mirairobot.core.factory;

import com.hhs.robot.mirairobot.dao.entity.RobotConfigEntity;
import net.mamoe.mirai.Bot;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hhs
 * @since 2020/11/25 15:20
 * 机器人容器
 */
public class BotContainer {

    private static List<Bot> botContainer = new ArrayList<>();

    private static Map<Bot, List<Long>> botGroup = new HashMap<>();

    // 机器人列表
    public static List<Bot> getBotContainer() {
        return botContainer;
    }

    public static void addGroupHandler(Bot bot, RobotConfigEntity robotConfigEntity) {
        if (bot != null) {
            botContainer.add(bot);
            // 机器人支持的群
            String[] split = robotConfigEntity.getGroup().split(",");
            List<Long> collect = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList());
            botGroup.put(bot, collect);
        }
    }

    /**
     * 检查机器人群
     *
     * @param botId 机器人id
     * @param qqGroup 机器人qq群
     * @return true 符合 false 不符合
     */
    public static boolean checkBotGroup(long botId, long qqGroup) {
        Bot bot = getBot(botId);
        if (bot == null) {
            return false;
        }
        List<Long> longs = botGroup.get(bot);
        return !CollectionUtils.isEmpty(longs) && longs.contains(qqGroup);
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

    /**
     * 通过机器人id获取机器人
     *
     * @param botId 机器人id
     * @return Bot
     */
    public static Bot getBot(long botId) {
        for (Bot bot : botContainer) {
            if (bot.getId() == (botId)) {
                return bot;
            }
        }

        return null;
    }

}
