package com.lzg.guli.order.service.impl;

import com.lzg.commonutils.vo.CourseWebInfoVo;
import com.lzg.commonutils.vo.MemberWebInfoVo;
import com.lzg.guli.order.entity.TOrder;
import com.lzg.guli.order.mapper.TOrderMapper;
import com.lzg.guli.order.service.RemoteCourseService;
import com.lzg.guli.order.service.RemoteMemberService;
import com.lzg.guli.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-20
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {


//    @Autowired
//    @Qualifier("remoteMemberService")
    @Resource
    public RemoteMemberService remoteMemberService;

//    @Autowired
//    @Qualifier("remoteCourseService")
    @Resource
    public RemoteCourseService remoteCourseService;

    @Override
    public String createPayLog(String courseId, String memberId) {
        //通过memberId远程调用接口获取信息
        MemberWebInfoVo memberWebInfoVo = remoteMemberService.remoteGetMemberById(memberId);

        //通过courseId远程调用接口获取信息
        CourseWebInfoVo courseWebInfoVo = remoteCourseService.remoteCourseInfoById(courseId);

        //根据这些信息创建一个新的订单号
        TOrder tOrder = new TOrder();
        //将数据进行再一次的转移
        tOrder.setOrderNo(UUID.randomUUID().toString().substring(0,10));
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseWebInfoVo.getTitle());
        tOrder.setCourseCover(courseWebInfoVo.getCover());
        tOrder.setTeacherName("test");
        tOrder.setTotalFee(courseWebInfoVo.getPrice());
        tOrder.setMobile(memberWebInfoVo.getMail());
        tOrder.setNickname(memberWebInfoVo.getNickname());
        tOrder.setStatus(0);
        tOrder.setPayType(1);

        baseMapper.insert(tOrder);
        return tOrder.getOrderNo();   //返回订单号
    }
}
