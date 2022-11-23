package com.lzg.guli2.aliyunvod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lzg.guli2.InitObject;

import java.util.List;

public class VodTest {
    public static void main(String[] args) {
        uploadVideo();
    }

    private static String  accessKeyId = "LTAI5tJi9fDmVz5EubtVkpNC";

    private static String accessKeySecret = "DSnfcM3VYT8xTwqD1fcUYqE4RPhx0k";

    public static void uploadVideo() {
        //上传之后视频的标题
        String title = "newVideo -upload by sdk";

        //本地视频在本机的位置
        String fileName = "F:/testVideo.mp4";

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId,accessKeySecret,title,fileName);

        //指定分片上传每个分片的大小
        request.setPartSize(1 * 1024 * 1024L);

        //指定分片上传时的并发数量
        request.setTaskNum(1);

        //是否开启断点续传，默认时关闭的
        request.setEnableCheckpoint(false);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.println("RequestId " + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.println("VideoId=" + response.getVideoId() + "\n");
        } else {
            //如果设置回调url无效，不影响视频上传，可以返回videoId同时会返回错误码，其他情况上传失败的时候，videoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.println("videoId=" + response.getVideoId() + "\n");
            System.out.println("ErrorCode=" + response.getCode() + "\n");
            System.out.println("ErrorMessage=" + response.getMessage() + "\n");
        }

    }

    public static String getPlayAuth() throws ClientException {
        //通过视频id来获取视频播放的凭证
        DefaultAcsClient client = InitObject.initVodClient(accessKeyId,accessKeySecret);

        //创建爱你获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("0ed113b0a10d4cc192408f1e8d315973");

        //调用初始化对象方法得到凭证
        response = client.getAcsResponse(request);
        System.out.println("playauth: " + response.getPlayAuth());

        return response.getPlayAuth();
    }


    public static void getPlayUrl() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient(accessKeyId,accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        try {
            request.setVideoId("0ed113b0a10d4cc192408f1e8d315973");

            response = client.getAcsResponse(request);

            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.println("PlayInfo.PlayUrl = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");


        } catch (Exception e) {
            System.out.println("ErrorMessage"+e.getLocalizedMessage());
        }

        System.out.println("RequestId = " + response.getRequestId() + "\n");

    }
}
