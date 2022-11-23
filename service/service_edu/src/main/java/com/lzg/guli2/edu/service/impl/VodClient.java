package com.lzg.guli2.edu.service.impl;

import com.lzg.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodClientFallback.class)
@Service
public interface VodClient {

    @GetMapping("/vod/deleteVideoByVideoId/{videoId}")
    public R deleteVideoByVideoId(@PathVariable("videoId") String videoId);


    @DeleteMapping("/vod/deleteMany")
    public R deleteVideoMany(@RequestParam("videoIdList") List<String> videoIdList);
}
