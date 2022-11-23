package com.lzg.guli.statistics.controller;


import com.lzg.commonutils.R;
import com.lzg.guli.statistics.client.MemberClient;
import com.lzg.guli.statistics.service.DailyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-23
 */
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {

    @Resource
    private DailyService dailyService;


    @GetMapping("countRegisterByDay/{day}")
    public R countRegisterByDay(@PathVariable("day") String day) {
        dailyService.createByDay(day);

        return R.ok();
    }



    @GetMapping("showChart/{begin}/{end}/{type}")
    public R showChart(@PathVariable("begin") String begin,
                       @PathVariable("end") String end,
                       @PathVariable("type") String type) {

        HashMap<String,Object> chartData = dailyService.getChart(begin,end,type);

        return R.ok().data("chartData",chartData);
    }



}

