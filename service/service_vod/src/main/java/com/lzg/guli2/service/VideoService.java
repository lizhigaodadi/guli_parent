package com.lzg.guli2.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(String accessKeyId, String accessKeySecret, MultipartFile file);
    void removeVideoById(String videoId);

    void removeMore(List<String> videoIdList);
}
