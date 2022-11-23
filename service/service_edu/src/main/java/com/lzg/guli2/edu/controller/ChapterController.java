package com.lzg.guli2.edu.controller;


import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.Chapter;
import com.lzg.guli2.edu.entity.vo.chapter.ChapterVo;
import com.lzg.guli2.edu.service.ChapterService;
import com.lzg.guli2.edu.service.VideoService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-01
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @Resource
    private VideoService videoService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("saveChapter")
    @ApiOperation(value = "新增章节")
    public R saveChapter(@ApiParam(value = "章节信息",name = "chapter",required = true) @RequestBody Chapter chapter) {
        boolean save = chapterService.save(chapter);
        if (!save) {
            throw new GuliException(20001,"章节信息保存失败");
        }
        return R.ok();
    }


    @GetMapping("deleteChapterById/{id}")
    @ApiOperation("通过id删除章节")
    public R deleteChapterById(@ApiParam(required = true,name = "id",value = "章节id") @PathVariable("id") String id) {
        boolean isDelete = chapterService.removeChapterById(id);

        return R.ok().data("isDelete",isDelete);
    }

    @PostMapping("updateChapter")
    @ApiOperation(value = "更新章节信息")
    public R updateChapterInfo(@ApiParam(value = "章节信息",name = "chapter",required = true) @RequestBody Chapter chapter) {
        boolean isUpdate = chapterService.updateById(chapter);
        return R.ok().data("isUpdate", isUpdate);
    }


    //根据章节id来查询章节信息
    @GetMapping("getChapterById/{id}")
    @ApiOperation(value = "根据id来查询章节")
    public R getChapterById(@ApiParam(name = "id",value = "章节id",required = true) @PathVariable("id") String id) {
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

    //根据id来查询


}

