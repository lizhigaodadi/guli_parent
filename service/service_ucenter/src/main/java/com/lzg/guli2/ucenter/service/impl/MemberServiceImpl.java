package com.lzg.guli2.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.commonutils.JwtUtils;
import com.lzg.commonutils.MD5Utils;
import com.lzg.commonutils.SendMailUtils;
import com.lzg.guli2.ucenter.entity.Member;
import com.lzg.guli2.ucenter.entity.vo.LoginVo;
import com.lzg.guli2.ucenter.entity.vo.RegisterVo;
import com.lzg.guli2.ucenter.mapper.MemberMapper;
import com.lzg.guli2.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public String login(LoginVo loginVo) {
        String mail = loginVo.getMail();
        String password = loginVo.getPassword();

        //开始后端校验
        log.info("判断邮箱和密码是否为空");
        if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"密码或邮箱不能为空");
        }

        //从数据库中搜寻对象
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mail",mail);
        Member member = baseMapper.selectOne(memberQueryWrapper);

        log.info("判断密码是否正确");
        if (!MD5Utils.md5(password).equals(member.getPassword())) {
            throw new GuliException(20001,"密码或邮箱错误");
        }

        //检验是否被警用
        log.info("判断账号是否可用");
        if (member.getIsDisabled()) {
            throw new GuliException(20001,"该用户已被封禁");
        }

        //利用jwt来生成token
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        log.info("返回token");
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mail = registerVo.getMail();
        String code = registerVo.getCode()+"";
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();

        //判断一下验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mail);

        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(code)) {
            throw new GuliException(20001,"验证码错误");
        }

        //验证码通过了，将数据加入到数据库中
        Member member = new Member();
        member.setMail(mail);
        member.setNickname(nickName);
        member.setPassword(MD5Utils.md5(password));   //这里的密码需要经过md5加密后再存入数据库中

        baseMapper.insert(member);

        //结束
    }

    @Override
    public void sendMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            throw new GuliException(20001,"邮箱不能为空");
        }

        //首先判断一下mail是否已经注册过了
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mail",mail);
        Integer count = baseMapper.selectCount(memberQueryWrapper);
        if (count != 0) {  //数据库中已经有过相同的邮箱注册过了
            throw new GuliException(20001,"该邮箱已被注册");
        }


        SendMailUtils sendMailUtils = new SendMailUtils();
        sendMailUtils.setTo(mail);
        String randomCode = sendMailUtils.senMail()+"";
        redisTemplate.opsForValue().set(mail,randomCode,5, TimeUnit.MINUTES);  //存入redis中，存活时间为5分钟
    }

    @Override
    public Member getMemberByOpenId(String openId) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid",openId);

        Member member = baseMapper.selectOne(memberQueryWrapper);

        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {

        Integer count = baseMapper.countRegisterByDay(day);
        return count;
    }
}
