package com.lzg.guli.order.service;

import com.lzg.commonutils.vo.MemberWebInfoVo;
import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter")
@Service(value = "remoteMemberService")
public interface RemoteMemberService {
    @GetMapping("/ucenter/member/remoteGetMemberById/{memberId}")
    public MemberWebInfoVo remoteGetMemberById(@PathVariable("memberId") String memberId);


}
