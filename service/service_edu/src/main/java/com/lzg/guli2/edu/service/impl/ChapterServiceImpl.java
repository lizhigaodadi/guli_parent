package com.lzg.guli2.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.guli2.edu.entity.Chapter;
import com.lzg.guli2.edu.entity.Video;
import com.lzg.guli2.edu.entity.vo.chapter.ChapterVo;
import com.lzg.guli2.edu.entity.vo.chapter.VideoVo;
import com.lzg.guli2.edu.mapper.ChapterMapper;
import com.lzg.guli2.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzg.guli2.edu.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@Service
@Slf4j
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //通过课程id来获取所有的chapter数据
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);
        log.info("chapters: {}",chapters);



        //通过课程id来获取video信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.orderByAsc("sort");  //通过sort来进行升序排序

        List<Video> videoList = videoService.list(videoQueryWrapper);

        log.info("videoList: {}",videoList);

        //最终的数据
        List<ChapterVo> finalResult = new ArrayList<>();

        //遍历他们，转化为vo对象
        for (int i=0; i<chapters.size() ;i++) {

            Chapter chapter = chapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);

            finalResult.add(chapterVo);

            //再次for循环找到当前章节下所有的video
            for (int j=0;j<videoList.size();j++) {
                Video video = videoList.get(j);

                if (chapter.getId().equals(video.getChapterId())) {
                    //找到了相对应的数据，进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);

                    //再将这个video加入到课程下
                    chapterVo.getChildren().add(videoVo);

                }
            }

        }


        return finalResult;
    }

    @Override
    public boolean removeChapterById(String id) {

        //首先通过章节信息获取章节下是否存在小节
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",id);

        int count = videoService.count(videoQueryWrapper);

        if (count>0) {
            //发现存在章节，就不继续删除
            return false;
        } else {

            //继续删除
            baseMapper.deleteById(id);

            return true;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);

    }
}
