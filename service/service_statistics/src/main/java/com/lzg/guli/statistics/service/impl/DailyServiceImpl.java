package com.lzg.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.guli.statistics.client.MemberClient;
import com.lzg.guli.statistics.entity.Daily;
import com.lzg.guli.statistics.mapper.DailyMapper;
import com.lzg.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-23
 */
@Service
@Slf4j
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    private MemberClient memberClient;

    @Override
    public void createByDay(String day) {

        //删除已经存在的对象
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.eq("date_calculated",day);
        baseMapper.delete(dailyQueryWrapper);

        Integer registerDay = memberClient.statisticsCount(day);
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);

        //创建一条新的记录
        Daily daily = new Daily();
        daily.setRegisterNum(registerDay);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(loginNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);

    }

    //根据条件进行一波条件查询
    @Override
    public HashMap<String, Object> getChart(String begin, String end, String type) {
        String typeGetter = "";  //存储getter方法的函数名称
        Method getter = null;

        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.between("date_calculated",begin,end);

        //控制搜索的字段
        dailyQueryWrapper.select("date_calculated",type);

        //进行搜索
        List<Daily> statisticsList = baseMapper.selectList(dailyQueryWrapper);

        //通过反射获取要执行的方法
        try {
            switch (type) {
                case "register_num":
                    getter = Daily.class.getDeclaredMethod("getRegisterNum");
                    break;
                case "login_num":
                    getter = Daily.class.getDeclaredMethod("getLoginNum");
                    break;
                case "video_view_num":
                    getter = Daily.class.getDeclaredMethod("getVideoViewNum");
                    break;
                case "course_num":
                    getter = Daily.class.getDeclaredMethod("getCourseNum");
                    break;
                default:
                    break;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new GuliException(20001,"反射获取getter方法失败");
        }

        log.info("getter:{}",getter);

        //创建存储最终结果的集合
        HashMap<String,Object> map = new HashMap<>();

        int size = statisticsList.size();

        //存储单一结果的容器
        List<String> dateList = new ArrayList<>(size);
        List<Object> dataList = new ArrayList<>(size);

        Daily daily = null;

        //循环执行方法
        for (int i=0;i<size;i++) {
            daily = statisticsList.get(i);

            //获取字段值分别存储进list中
            try {
                dataList.add(getter.invoke(daily));
                dateList.add(daily.getDateCalculated());
            } catch (Exception e) {
                e.printStackTrace();
                throw new GuliException(20001,"通过反射执行getter方法失败");
            }

        }

        map.put("dataList",dataList);
        map.put("dateList",dateList);

        return map;
    }
}
