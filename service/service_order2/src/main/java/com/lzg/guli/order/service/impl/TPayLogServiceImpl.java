package com.lzg.guli.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lzg.commonutils.HttpClient;
import com.lzg.guli.order.entity.TOrder;
import com.lzg.guli.order.entity.TPayLog;
import com.lzg.guli.order.mapper.TPayLogMapper;
import com.lzg.guli.order.service.TOrderService;
import com.lzg.guli.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzg.sevicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Resource
    private TOrderService tOrderService;

    @Override
    public HashMap<String, Object> createNative(String orderNo) {

        try {
            //通过订单号查询订单信息
            QueryWrapper<TOrder> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("order_no",orderNo);

            TOrder order = tOrderService.getOne(orderQueryWrapper);

            Map m = new HashMap<>();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("0.01")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数 ,引入自己的工具类
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String content = client.getContent();

            //将有效的数据重新封装到一个新的map中
            HashMap<String,Object> resultMap = new HashMap<>();

            resultMap.put("out_trade_no", orderNo);
            resultMap.put("course_id", order.getCourseId());
            resultMap.put("total_fee", order.getTotalFee());
            resultMap.put("result_code", resultMap.get("result_code"));
            resultMap.put("code_url", resultMap.get("code_url"));

            return resultMap;

        } catch (Exception e){

            e.printStackTrace();

            throw new GuliException(20001,"请求支付二维码失败");
        }

    }

    @Override
    public Map<String, String> queryPayService(String orderNo) {
        //通过微信接口获取订单信息
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String,String> resultMap = (HashMap<String, String>) WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //更新一下支付记录
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单信息
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<TOrder> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNo);

        TOrder order = tOrderService.getOne(orderQueryWrapper);

        //检测订单状态
        if (order.getStatus() == 1) return;

        //更新订单状态
        order.setStatus(1);
        tOrderService.updateById(order);

        //添加一条支付记录
        TPayLog payLog = new TPayLog();

        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        payLog.setPayType(1);
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));


        baseMapper.insert(payLog);   //插入到支付日志表中
    }


}
