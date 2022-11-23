package com.lzg.guli2.edu.service;

import com.lzg.guli2.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzg.guli2.edu.entity.vo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
public interface ChapterService extends IService<Chapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    boolean removeChapterById(String id);

    void removeChapterByCourseId(String courseId);
}
