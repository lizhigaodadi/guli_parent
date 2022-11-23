package com.lzg.guli2.cms.controller;

import com.lzg.commonutils.R;
import com.lzg.guli2.cms.entity.CrmBanner;
import com.lzg.guli2.cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//供普通用户使用的接口
@RestController
@CrossOrigin
@RequestMapping("cms/front")
public class BannerFrontController {

    @Resource
    public CrmBannerService crmBannerService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //获取所有的Banner对象
    @GetMapping("getAllBanner")
    public R getAllBanner() {
//        //发个邮件
//        SendMailUtils sendMailUtils = new SendMailUtils();
//        sendMailUtils.setTo("2507959808@qq.com");
//        Integer code = sendMailUtils.senMail();
//        if (code == 0) {
//            throw new GuliException(20001,"邮件发送失败");
//        }



        //获取所有,这里通过自己实现接口来查询,方便之后redis的优化
        List<CrmBanner> crmBannerList = crmBannerService.getAllBanner();

        return R.ok().data("items",crmBannerList);
    }
}
