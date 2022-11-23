package com.lzg.guli2.edu.service;

import com.lzg.guli2.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface VideoService extends IService<Video> {

    void removeByCourseId(String courseId);
}
