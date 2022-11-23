package com.lzg.guli.order.controller;


import com.lzg.commonutils.R;
import com.lzg.guli.order.service.TPayLogService;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@RestController
@RequestMapping("/order/payLog")
public class TPayLogController {

    @Resource
    private TPayLogService tPayLogService;


    @GetMapping("getWXCode/{orderNo}")
    public R getWxCode(@PathVariable("orderNo") String orderNo) {
        HashMap<String,Object> map = tPayLogService.createNative(orderNo);

        return R.ok().data(map);
    }


    //通过订单号来查询微信支付状态
    @GetMapping("queryPayLogStatus/{orderNo}")
    public R queryPayLogStatus(@PathVariable("orderNo") String orderNo) {
        Map<String,String> map = tPayLogService.queryPayService(orderNo);
        if (map == null) {
            throw new GuliException(20001,"支付出错");
        }

        if (map.get("trade_state").equals("SUCCESS")) {
            //添加一条支付记录
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("订单支付成功");
        }



        return R.ok().message("订单未支付");
    }
}

