package com.lzg.guli2.edu.service.impl;

import com.lzg.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientFallback implements VodClient{
    @Override
    public R deleteVideoByVideoId(String videoId) {
        return R.error().message("删除失败");
    }

    @Override
    public R deleteVideoMany(List<String> videoIdList) {
        return R.error().message("可能是404呢");
    }
}
