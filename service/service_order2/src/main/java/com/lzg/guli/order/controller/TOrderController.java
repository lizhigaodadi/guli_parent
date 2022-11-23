package com.lzg.guli.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzg.commonutils.JwtUtils;
import com.lzg.commonutils.R;
import com.lzg.guli.order.entity.TOrder;
import com.lzg.guli.order.service.TOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@RestController
@RequestMapping("/order/")
@CrossOrigin
@Slf4j
public class TOrderController {

    @Resource
    private TOrderService tOrderService;

    @PostMapping("createPayLog/{courseId}")
    public R createPayLog(@PathVariable("courseId") String courseId, HttpServletRequest request) {   //生成订单 返回订单id
        //解析token
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        String orderId = tOrderService.createPayLog(courseId,memberId);
        return R.ok().data("orderId",orderId);
    }


    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable("orderNo") String orderNo) {
        QueryWrapper<TOrder> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNo);
        TOrder order = tOrderService.getOne(orderQueryWrapper);
        return R.ok().data("order",order);
    }



}

