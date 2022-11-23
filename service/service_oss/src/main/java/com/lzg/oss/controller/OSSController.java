package com.lzg.oss.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.lzg.commonutils.R;
import com.lzg.oss.utils.OSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OSSController {

    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file) {

        log.info("endPoint:{} keyId:{} keySecret:{} bucket_name:{}",OSSUtils.END_POINT,OSSUtils.KEY_ID,OSSUtils.KEY_SECRET,OSSUtils.BUCKET_NAME);
        System.out.println(OSSUtils.BUCKET_NAME+OSSUtils.KEY_SECRET+OSSUtils.KEY_ID+OSSUtils.END_POINT);

        //ossClient
        OSS ossClient = new OSSClientBuilder().build(OSSUtils.END_POINT,OSSUtils.KEY_ID,OSSUtils.KEY_SECRET);
        InputStream inputStream = null;
        try {
            //获取文件输入流
            inputStream = file.getInputStream();
            //获取文件名称，并生成一个新的文件名称

            //通过日期来创建相应的文件夹
            //原生jdk的创建
//            Date date = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            String currentDay = simpleDateFormat.format(date);

            //通过工具类来创建
            String currentDay = new DateTime().toString("yyyy/MM/dd");


            System.out.println(file.getOriginalFilename());
            String fileName = currentDay + "/" + UUID.randomUUID().toString().replaceAll("-","") + file.getOriginalFilename() ;



            //写入到oss中
            ossClient.putObject(OSSUtils.BUCKET_NAME,fileName,inputStream);

            //装配出新的url   https://wangxm.oss-cn-hangzhou.aliyuncs.com/33.JPG
            String url = "http://" + OSSUtils.BUCKET_NAME + "." + OSSUtils.END_POINT + "/" + fileName;

            return R.ok().data("url",url);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流和ossClient
             ossClient.shutdown();

        }
        return null;

    }

}
