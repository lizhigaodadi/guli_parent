package com.lzg.guli2.cms.service;

import com.lzg.guli2.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner> getAllBanner();
}
