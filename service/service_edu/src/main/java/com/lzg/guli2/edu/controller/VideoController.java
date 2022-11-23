package com.lzg.guli2.edu.controller;


import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Video;
import com.lzg.guli2.edu.service.VideoService;
import com.lzg.guli2.edu.service.impl.VodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
@Slf4j
public class VideoController {

    @Resource
    private VideoService videoService;

    @Autowired   //注入远程调用接口
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video) {
        boolean save = videoService.save(video);
        return R.ok();
    }


    @GetMapping("{id}")
    public R deleteVideoById(@PathVariable("id") String id) {
        //通过id来查询出目标video的videoSourceId
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();

        //检测一下是否存在videoSourceId
        if (StringUtils.isEmpty(videoSourceId)) {
            //rpc远程调用
            vodClient.deleteVideoByVideoId(videoSourceId);

        }


        boolean b = videoService.removeById(id);
        return R.ok();
    }

    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable("id") String id) {
        log.info("videoId: {}",id);
        Video video = videoService.getById(id);


        return R.ok().data("video",video);
    }

    @PostMapping("updateVideo")
    public R updateVideoById(@RequestBody Video video) {
        boolean b = videoService.updateById(video);
        return R.ok();
    }

}

