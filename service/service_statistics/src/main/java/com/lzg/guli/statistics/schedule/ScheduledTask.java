package com.lzg.guli.statistics.schedule;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.lzg.guli.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ScheduledTask {    //编写一些定时任务的类


    @Resource
    private DailyService dailyService;


    @Scheduled(cron = "0 0 1 ? * ? ")
    public void recordStatistics() {   //保存一些统计记录，在每天的凌晨

        //获取昨天的时间
        DateTime yesterday = DateUtil.yesterday();
        String day = yesterday.toString("yyyy-MM-dd");
        //开始记录昨天的统计数据
        dailyService.createByDay(day);

        log.info("=================记录 {} 的统计数据===================",day);

    }
}
