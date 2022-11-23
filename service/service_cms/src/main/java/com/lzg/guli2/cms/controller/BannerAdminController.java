package com.lzg.guli2.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzg.commonutils.R;
import com.lzg.guli2.cms.entity.CrmBanner;
import com.lzg.guli2.cms.service.CrmBannerService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/cms/admin")
public class BannerAdminController {
    @Resource
    private CrmBannerService crmBannerService;


    //增加
    @ApiOperation("添加一个新的banner对象")
    @PutMapping("add")
    public R addBanner(@ApiParam(required = true,name = "banner",value = "banner信息") @RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return R.ok();
    }


    //根据id来删除一个banner
    @ApiOperation("根据id来删除一个对象")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@ApiParam(required = true,value = "删除对象的id",name = "id") @PathVariable("id") String id) {
        if (!crmBannerService.removeById(id)) {
            throw new GuliException(20001,"banner对象删除失败");
        }
        return R.ok();
    }


    //修改banner信息
    @PutMapping("updateBanner")
    public R update(@RequestBody CrmBanner crmBanner) {
        if (!crmBannerService.updateById(crmBanner)) {
            throw new GuliException(20001,"banner对象修改失败");
        }
        return R.ok();
    }


    //通过id来查找对象
    @GetMapping("selectById/{id}")
    public R selectById(@PathVariable("id") String id) {
        CrmBanner crmBanner = crmBannerService.getById(id);
        if (crmBanner == null) {
            throw new GuliException(20001,"未查询到相关对象");
        }
        return R.ok().data("crmBanner",crmBanner);
    }


    //分页查询所有的Banner对象
    @GetMapping("getBannerByPage/{limit}/{page}")
    public R getBannerByPage(@PathVariable("limit") Long limit,
                             @PathVariable("page") Long page) {
        Page<CrmBanner> bannerPage = new Page<>(page,limit);

        //开始进行查询
        crmBannerService.page(bannerPage,null);

        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());

    }

}
