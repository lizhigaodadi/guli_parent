package com.lzg.commonutils;


import io.swagger.models.auth.In;
import lombok.SneakyThrows;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SendMailUtils {
    private static String FROM = "lizhigao521123@163.com";   //发件方
    private static String AUTH = "WULVSIILFVBDBPHI";   //开启smtp服务时设置都密钥，非邮箱登录密码
    private String to = "";   //收件方

    public int getRandomCode() {
        int randomCode = (int) (Math.random() * 8000) + 1000;
        return randomCode;
    }
    public void setTo(String to) {
        this.to = to;
    }

    public Integer senMail() {
        //获取随机验证
        int randomCode = getRandomCode();

        //设置邮件内容
        String contentString = "尊敬的用户，感谢您注册谷粒学院，您的验证码为 <h3>" + randomCode + "</h3> ,请尽快输入验证码，以完成注册";
        Properties properties = new Properties();
        //设置一些必要的参数
        properties.put("mail.host","smtp.163.com");   //?smtp.163.com ?smtp.yeah.net
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);    //开启发邮件时debug

        try {
            new Thread(new Runnable(){

                @SneakyThrows
                @Override
                public void run() {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(FROM));
                    message.setHeader("Header","谷粒商城激活验证");
                    message.setRecipient(Message.RecipientType.CC,new InternetAddress(FROM));   //先给自己抄送一份，可以减少出现554垃圾邮件
                    message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
                    message.setSubject("谷粒学院激活验证");   //设置邮箱主题
                    Multipart multipart = new MimeMultipart();   //向multipart中家唉邮箱的各个部分内容，包括文本和邮件
                    BodyPart contentPart = new MimeBodyPart();
                    contentPart.setContent(contentString,"text/html; charset=utf-8"); //设置文本内容和文本的编码格式
                    multipart.addBodyPart(contentPart);
                    message.setContent(multipart);
                    message.saveChanges();   //保存变化

                    //设置链接服务器的邮箱
                    Transport transport = session.getTransport("smtp");  //设置协议
                    transport.connect("smtp.163.com",FROM,AUTH);   //auth是开启smtp是设置的证书，不是邮箱密码
                    transport.sendMessage(message,message.getAllRecipients());
                    System.out.println("成功发送完邮件");
                    //关闭
                    transport.close();
                }
            }).start();
            return randomCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }



}

