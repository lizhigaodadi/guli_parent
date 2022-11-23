package com.lzg.guli2.ucenter.controller;

import com.google.gson.Gson;
import com.lzg.commonutils.JwtUtils;
import com.lzg.guli2.ucenter.entity.Member;
import com.lzg.guli2.ucenter.service.MemberService;
import com.lzg.guli2.ucenter.utils.HttpClientUtils;
import com.lzg.guli2.ucenter.utils.WeiXinUtils;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WeiXinController {

    @Resource
    private MemberService memberService;

    @GetMapping("login")
    public String callBack() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";

        //对redirect_url进行url编码
        String redirectUrl = WeiXinUtils.REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("appId: {}",WeiXinUtils.APP_ID);
        String url = String.format(baseUrl,
                WeiXinUtils.APP_ID,
                redirectUrl,
                "lzg"
        );

        return "redirect:" + url;

    }

    @GetMapping("callback")
    public String callback(String code,String state) {
        //打印临时授权票据
        log.info("code:{},state:{}",code,state);

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl, WeiXinUtils.APP_ID, WeiXinUtils.APP_SECRET, code);

        String result = "";  //http请求返回的结果

        try {
            result = HttpClientUtils.get(accessTokenUrl);
            log.info("accessToken:{}",result);
        } catch (Exception e) {
            throw new GuliException(20001, "http请求微信服务器失败");
        }

        //通过Gson来解析json字符串
        Gson gson = new Gson();
        HashMap accessTokenInfo = gson.fromJson(result, HashMap.class);

        //获取accessToken和openId
        String accessToken = (String) accessTokenInfo.get("access_token");
        String openId = (String) accessTokenInfo.get("openid");


        //根据openId从数据库中拉取对象，来检验这个账号是否已经注册过了
        Member member = memberService.getMemberByOpenId(openId);
        if (member == null) {
            //发现该账号还没有被注册过
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
            "?access_token=%s" +
            "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl,
                    accessToken,
                    openId);

            //发送请求
            String resultUserInfo = "";
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20001,"请求微信用户信息失败");
            }

            //解析json
            HashMap userInfo = gson.fromJson(resultUserInfo,HashMap.class);
            String nickname = (String) userInfo.get("nickname");
            String headimgurl = (String) userInfo.get("headimgurl");


            //向数据库中插入一条新的数据
            member= new Member();
            member.setNickname(nickname);
            member.setOpenid(openId);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //通过id和nickname生成一个jwtToken
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        //重定向
        return "redirect:http://localhost:3000?token=" + token;   //跳回首页
     }



}
