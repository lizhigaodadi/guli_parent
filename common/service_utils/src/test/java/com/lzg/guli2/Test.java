package com.lzg.guli2;

import com.lzg.commonutils.MD5Utils;
import com.lzg.commonutils.SendMailUtils;

public class Test {

    @org.junit.Test
    public void testMail() {
        SendMailUtils sendMailUtils = new SendMailUtils();
        sendMailUtils.setTo("lizhigao521123@163.com");
        Integer code = sendMailUtils.senMail();

        System.out.println("验证码为: "+code);
    }


    @org.junit.Test
    public void testMd5() {
        String s = MD5Utils.md5("111111");
        System.out.println(s);
    }
}
