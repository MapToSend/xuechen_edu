package com.online.edu.ucservice.mapper;

import com.online.edu.ucservice.bean.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer queryUcenter(String date);
}
