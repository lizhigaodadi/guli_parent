package com.lzg.guli2.edu.service.impl;

import com.lzg.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class CommentClientFallback implements CommentClient{

    @Override
    public R remoteParseToken(String token) {
        return R.error().message("远程调用执行解析token失败");
    }
}
