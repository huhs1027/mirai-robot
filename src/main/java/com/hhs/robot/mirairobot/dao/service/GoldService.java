package com.hhs.robot.mirairobot.dao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhs.robot.mirairobot.dao.entity.GoldEntity;
import com.hhs.robot.mirairobot.dao.entity.GoldRecordEntity;
import com.hhs.robot.mirairobot.dao.mapper.GoldMapper;
import com.hhs.robot.mirairobot.dao.mapper.GoldRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author hhs
 * @since 2020/11/25 18:08
 */
@Service
public class GoldService {

    @Autowired
    private GoldRecordMapper goldRecordMapper;

    @Autowired
    private GoldMapper goldMapper;

    /**
     * 增加金币
     *
     * @param qq   qq
     * @param num  数量
     * @param type 类型
     */
    @Transactional(rollbackFor = Exception.class)
    public void addGold(long qq, int num, String type) {
        // 增加记录
        GoldRecordEntity goldRecordEntity = new GoldRecordEntity();
        goldRecordEntity.setQq(qq);
        goldRecordEntity.setNum(num);
        goldRecordEntity.setRecordType(type);
        goldRecordEntity.setDayTime(LocalDateTime.now());
        goldRecordMapper.insert(goldRecordEntity);

        // 增加总表
        QueryWrapper<GoldEntity> goldEntityQueryWrapper = new QueryWrapper<>();
        goldEntityQueryWrapper.eq("qq", qq);
        GoldEntity goldEntity = goldMapper.selectOne(goldEntityQueryWrapper);
        if (goldEntity == null) {
            // 新增
            GoldEntity record = new GoldEntity();
            record.setQq(qq);
            record.setGold(num);
            goldMapper.insert(record);
        } else {
            // 更新
            goldEntity.setGold(goldEntity.getGold() + num);
            goldMapper.updateById(goldEntity);
        }

    }

    /**
     * 查总金币
     *
     * @param qq qq
     * @return 总金币数
     */
    public int queryGold(long qq) {
        QueryWrapper<GoldEntity> goldEntityQueryWrapper = new QueryWrapper<>();
        goldEntityQueryWrapper.eq("qq", qq);
        GoldEntity goldEntity = goldMapper.selectOne(goldEntityQueryWrapper);
        if (goldEntity != null) {
            return goldEntity.getGold();
        }
        return 0;
    }
}
