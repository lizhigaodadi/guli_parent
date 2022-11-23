package com.lzg.guli.statistics.client;

import com.lzg.guli.statistics.client.callback.MemberClientCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter",fallback = MemberClientCallback.class)
public interface MemberClient {


    @GetMapping("ucenter/member/statisticsCount/{day}")
    Integer statisticsCount(@PathVariable("day") String day);
}
