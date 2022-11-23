package com.lzg.guli2.ucenter.controller;


import com.lzg.commonutils.JwtUtils;
import com.lzg.commonutils.R;
import com.lzg.commonutils.vo.MemberWebInfoVo;
import com.lzg.guli2.ucenter.entity.Member;
import com.lzg.guli2.ucenter.entity.vo.LoginVo;
import com.lzg.guli2.ucenter.entity.vo.RegisterVo;
import com.lzg.guli2.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
@Slf4j
public class MemberController {


    @Resource
    private MemberService memberService;

    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {

        String token = memberService.login(loginVo);


        return R.ok().data("token",token);
    }


    @GetMapping("sendMail/{mail}")
    public R sendMail(@ApiParam(value = "邮箱",name = "mail") @PathVariable("mail") String mail) {
        memberService.sendMail(mail);

        return R.ok();
    }


    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        log.info("registerVo:{}",registerVo);
        memberService.register(registerVo);

        return R.ok();
    }


    @GetMapping("parseToken")
    @ApiOperation(value = "解析token返回对象信息")
    public R parseToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //通过数据库查询信息
        Member user = memberService.getById(memberId);
        return R.ok().data("userInfo",user);
    }

    @GetMapping("remoteParseToken/{token}")
    @ApiOperation(value = "远程调用解析token接口")
    public R remoteParseToken(@PathVariable String token) {
        //解析token
        String memberId = JwtUtils.parseToken(token);

        //通过数据库查询信息
        Member member = memberService.getById(memberId);
        //将member转换为map进行存储
        HashMap<String,String> user = new HashMap<>();
        user.put("id",member.getId());
        user.put("avatar",member.getAvatar());
        user.put("nickname",member.getNickname());

        return R.ok().data("user",user);
    }


    @GetMapping("remoteGetMemberById/{memberId}")
    public MemberWebInfoVo remoteGetMemberById(@PathVariable("memberId") String memberId) {
        Member member = memberService.getById(memberId);

        MemberWebInfoVo memberWebInfoVo = new MemberWebInfoVo();   //创建专门用于网络传输的对象

        BeanUtils.copyProperties(member,memberWebInfoVo);  //进行数据的迁移
        return memberWebInfoVo;
    }



    //统计
    @GetMapping("statisticsCount/{day}")
    public Integer statisticsCount(@PathVariable("day") String day) {

        Integer count = memberService.countRegisterByDay(day);
        log.info(day+" 注册人数为: " + count);
        return count;
    }

}

