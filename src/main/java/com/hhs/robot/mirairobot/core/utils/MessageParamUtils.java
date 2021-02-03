package com.hhs.robot.mirairobot.core.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 消息工具类
 *
 * @author hhs
 * @since 2021/2/3 10:44
 */
public class MessageParamUtils {

    public static List<String> getParam(String messageContent) {
        ArrayList<String> objects = new ArrayList<>();
        if (StringUtils.isBlank(messageContent)) {
            return objects;
        }

        String[] s = messageContent.split(" ");
        if (s.length > 0) {
            Collections.addAll(objects, s);
            objects.remove(0);
        }

        return objects;
    }

}
