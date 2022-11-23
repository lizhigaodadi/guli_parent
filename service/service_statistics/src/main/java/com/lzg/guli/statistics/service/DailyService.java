package com.lzg.guli.statistics.service;

import com.lzg.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-23
 */
public interface DailyService extends IService<Daily> {
    void createByDay(String day);

    HashMap<String, Object> getChart(String begin, String end, String type);
}
