package com.lzg.guli2.cms.service.impl;

import com.lzg.guli2.cms.entity.CrmBanner;
import com.lzg.guli2.cms.mapper.CrmBannerMapper;
import com.lzg.guli2.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-13
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Resource
    private CrmBannerMapper crmBannerMapper;

    @Override
    @Cacheable(value = "banner" ,key = "'selectIndexList'")
    public List<CrmBanner> getAllBanner() {
        return baseMapper.selectList(null);
    }
}
