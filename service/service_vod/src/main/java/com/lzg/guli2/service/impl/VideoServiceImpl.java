package com.lzg.guli2.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lzg.guli2.InitObject;
import com.lzg.guli2.service.VideoService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
@Slf4j
public class VideoServiceImpl implements VideoService {


    @Override
    public String uploadVideo(String accessKeyId, String accessKeySecret, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf('.'));
        InputStream is;
        String videoId = null;


        try {
            //开始进行视频上传操作
            is = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId,accessKeySecret,title,fileName,is);

            UploadVideoImpl upload = new UploadVideoImpl();
            UploadStreamResponse response = upload.uploadStream(request);

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
                log.warn("code: " + response.getCode()+" message: "+response.getMessage());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


        return videoId;
    }


    @Value("${aliyun.keyId}")
    private String accessKeyId;

    @Value("${aliyun.keySecret}")
    private String accessKeySecret;

    @Override
    public void removeVideoById(String videoId) {

        log.info(accessKeyId);
        log.info(accessKeySecret);
        try {
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);

            client.getAcsResponse(request);   //这里不要接受返回值


        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }
    }

    @Override
    public void removeMore(@RequestParam("videoIdList") List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();

            String videoIds = StringUtils.join(videoIdList.toArray(),',');

            request.setVideoIds(videoIds);

            client.getAcsResponse(request);   //这里不要接受返回值


        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }
    }
}
