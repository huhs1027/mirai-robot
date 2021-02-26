package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.annon.Commend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.RandomUtils;
import com.hhs.robot.mirairobot.dao.entity.ShutUpEntity;
import com.hhs.robot.mirairobot.dao.mapper.ShutUpMapper;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author hhs
 * @since 2021/2/25 15:57
 */
@Commend(group = { 467236828 }, commend = false)
public class RepeaterCommend implements GroupEventCommend {

    @Autowired
    private ShutUpMapper shutUpMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {
        // 随机 10%
        if (RandomUtils.win(1)) {
            return new MessageVO().add(groupMessageEvent.getMessage());
        }
        return null;
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        // 过滤列表
        ArrayList<Long> filterList = new ArrayList<>();
        // 图图机器人
        filterList.add(2854200117L);

        boolean robotContains = groupMessageEvent.getMessage().contentToString().contains("at:2854200117");

        // 指令关闭复读
        Supplier<List<ShutUpEntity>> supplier = () -> {
            QueryWrapper<ShutUpEntity> entityQueryWrapper = new QueryWrapper<>();
            entityQueryWrapper.gt("expire", LocalDateTime.now());
            return shutUpMapper.selectList(entityQueryWrapper);
        };

        return !filterList.contains(groupMessageEvent.getSender().getId()) && !robotContains && CollectionUtils.isEmpty(supplier.get());
    }

    @Override
    public int sort() {
        return Integer.MAX_VALUE;
    }
}
