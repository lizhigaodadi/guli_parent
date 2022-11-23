package com.lzg.guli.order.service;

import com.lzg.guli.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
public interface TOrderService extends IService<TOrder> {

    String createPayLog(String courseId, String memberId);
}
