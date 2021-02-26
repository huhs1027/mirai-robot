package com.hhs.robot.mirairobot.app.handler.group;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.app.handler.GroupEventCommend;
import com.hhs.robot.mirairobot.core.annon.Commend;
import com.hhs.robot.mirairobot.core.component.MessageVO;
import com.hhs.robot.mirairobot.core.utils.MessageParamUtils;
import com.hhs.robot.mirairobot.dao.entity.ShutUpEntity;
import com.hhs.robot.mirairobot.dao.mapper.ShutUpMapper;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author hhs
 * @since 2021/2/25 15:57
 */
@Commend(group = { 467236828 })
public class ShupUpCommend implements GroupEventCommend {

    @Autowired
    private ShutUpMapper shutUpMapper;

    @Override
    public MessageVO handlerGroupMessage(GroupMessageEvent groupMessageEvent) {

        // 非管理员无效
        MemberPermission permission = groupMessageEvent.getSender().getPermission();
        if (permission.name().equals(MemberPermission.MEMBER.name())) {
            return new MessageVO().addText("你让我闭嘴就闭嘴?");
        }

        List<String> param = MessageParamUtils.getParam(groupMessageEvent.getMessage().contentToString());
        int expire = 30;
        // 时间 单位分钟
        if (param.size() > 0) {
            String timestr = param.get(0);
            try {
                Integer time = Integer.valueOf(timestr);
                expire = time;
            } catch (Exception e) {
                // 不是数字,默认半小时
            }
        }

        ShutUpEntity shutUpEntity = new ShutUpEntity();
        shutUpEntity.setBotQq(groupMessageEvent.getBot().getId());
        shutUpEntity.setGroupQq(groupMessageEvent.getGroup().getId());
        shutUpEntity.setExpire(LocalDateTime.now().plusMinutes(expire));

        shutUpMapper.insert(shutUpEntity);

        return new MessageVO().addText("呜呜呜!~");
    }

    @Override
    public boolean match(GroupMessageEvent groupMessageEvent) {
        Supplier<List<ShutUpEntity>> supplier = () -> {
            QueryWrapper<ShutUpEntity> entityQueryWrapper = new QueryWrapper<>();
            entityQueryWrapper.gt("expire", LocalDateTime.now());
            return shutUpMapper.selectList(entityQueryWrapper);
        };
        return groupMessageEvent.getMessage().contentToString().startsWith(".闭嘴") && CollectionUtils.isEmpty(supplier.get());
    }

    @Override
    public int sort() {
        return Integer.MAX_VALUE - 1;
    }
}
