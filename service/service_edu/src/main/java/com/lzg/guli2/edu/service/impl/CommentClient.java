package com.lzg.guli2.edu.service.impl;

import com.lzg.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = CommentClientFallback.class)   //设置远程调用的服务和回调函数
@Service
public interface CommentClient {

    @GetMapping("/ucenter/member/remoteParseToken/{token}")
    @ApiOperation(value = "远程调用解析token接口")
    public R remoteParseToken(@PathVariable("token") String token);

}
