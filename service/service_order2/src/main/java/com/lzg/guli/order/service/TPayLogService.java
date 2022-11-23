package com.lzg.guli.order.service;

import com.lzg.guli.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
public interface TPayLogService extends IService<TPayLog> {

    HashMap<String,Object> createNative(String orderNo);

    Map<String, String> queryPayService(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
