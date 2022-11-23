package com.lzg.guli2.ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeiXinUtils implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String app_id;

    @Value("${wx.open.app_secret}")
    private String app_secret;

    @Value("${wx.open.redirect_url}")
    private String redirect_url;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        //为静态变量赋值
        APP_ID = this.app_id;
        APP_SECRET = this.app_secret;
        REDIRECT_URL = this.redirect_url;
    }
}
