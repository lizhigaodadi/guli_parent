package com.lzg.guli2.ucenter.mapper;

import com.lzg.guli2.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    Integer countRegisterByDay(@Param("day") String day);
}
