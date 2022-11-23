package com.lzg.guli2.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lzg.commonutils.R;
import com.lzg.guli2.InitObject;
import com.lzg.guli2.service.VideoService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "/vod")
@CrossOrigin
public class VideoController {

    @Resource
    private VideoService videoService;

    @Value("${aliyun.keyId}")
    private String accessKeyId;

    @Value("${aliyun.keySecret}")
    private String accessKeySecret;


    @PostMapping("uploadVideo")
    public R uploadVideoFile(MultipartFile file) {
        String videoId = videoService.uploadVideo(accessKeyId, accessKeySecret, file);

        return R.ok().data("videoId",videoId);
    }

    //根据视频id从阿里云中删除视频

    @GetMapping("deleteVideoByVideoId/{id}")
    public R deleteVideoByVideoId(@PathVariable("id") String videoId) {
        videoService.removeVideoById(videoId);
        return R.ok();
    }

    @DeleteMapping("deleteMany")
    public R deleteVideoMany(@RequestParam("videoIdList") List<String> videoIdList) {
        videoService.removeMore(videoIdList);
        return R.ok();
    }


    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable("vid") String vid) {
        //初始化对象
        try {
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);

            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //设置视频id
            request.setVideoId(vid);

            //调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);

        } catch (ClientException e) {
            throw new GuliException(20001,"获取视频播放凭证失败");
        }

    }
}
