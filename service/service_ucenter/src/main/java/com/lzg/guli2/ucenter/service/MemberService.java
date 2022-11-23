package com.lzg.guli2.ucenter.service;

import com.lzg.guli2.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzg.guli2.ucenter.entity.vo.LoginVo;
import com.lzg.guli2.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    void sendMail(String mail);

    Member getMemberByOpenId(String openId);

    Integer countRegisterByDay(String day);
}
