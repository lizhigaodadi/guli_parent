package com.lzg.guli.statistics.client.callback;

import com.lzg.guli.statistics.client.MemberClient;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Service;

@Service
public class MemberClientCallback implements MemberClient {
    @Override
    public Integer statisticsCount(String day) {
//        throw new GuliException(20001,"远程调用统计"+day+"注册人数失败");
        return 999;
    }
}
