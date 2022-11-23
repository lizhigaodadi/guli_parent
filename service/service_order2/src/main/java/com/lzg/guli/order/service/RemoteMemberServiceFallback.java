package com.lzg.guli.order.service;

import com.lzg.commonutils.vo.MemberWebInfoVo;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class RemoteMemberServiceFallback implements RemoteMemberService{
    @Override
    public MemberWebInfoVo remoteGetMemberById(String memberId) {
        throw new GuliException(20001,"远程调用获取对象信息失败");
    }
}
