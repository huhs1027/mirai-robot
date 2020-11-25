package com.hhs.robot.mirairobot.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhs.robot.mirairobot.dao.entity.DaGongEntity;
import com.hhs.robot.mirairobot.dao.mapper.DaGongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hhs
 * @since 2020/11/25 19:11
 */
@Service
public class DaGongService {

    @Autowired
    private DaGongMapper daGongMapper;

    /**
     * 获取上次打工记录
     *
     * @param qq qq
     * @return 打工记录
     */
    public DaGongEntity queryLast(long qq) {
        QueryWrapper<DaGongEntity> daGongEntityQueryWrapper = new QueryWrapper<>();
        daGongEntityQueryWrapper.eq("qq", qq);
        daGongEntityQueryWrapper.orderByDesc("last_time");

        Page<DaGongEntity> page = new Page<>(1, 1, false);
        Page<DaGongEntity> daGongEntityPage = daGongMapper.selectPage(page, daGongEntityQueryWrapper);
        List<DaGongEntity> records = daGongEntityPage.getRecords();
        if (records != null && !records.isEmpty()) {
            return records.get(0);
        }
        return null;
    }
}
