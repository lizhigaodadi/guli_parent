package com.lzg.guli2.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Video;
import com.lzg.guli2.edu.mapper.VideoMapper;
import com.lzg.guli2.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VodClient vodClient; //注入rpc调用接口

    @Override
    public void removeByCourseId(String courseId) {
        //这里先将所有的video_source_id查询出来


        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.select("video_source_id");

        List<Video> videos = baseMapper.selectList(videoQueryWrapper);
        List<String> videoIdList = new LinkedList<>();
        for (int i=0;i<videos.size();i++) {
            Video video = videos.get(i);

            if (!StringUtils.isEmpty(video.getVideoSourceId())) {
                //发现不是空的
                videoIdList.add(video.getVideoSourceId());
            }

        }

        if (videoIdList.size() > 0) {
            R r = vodClient.deleteVideoMany(videoIdList);
            log.warn(r.getMessage());

        }

        videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);


        baseMapper.delete(videoQueryWrapper);
    }






}
