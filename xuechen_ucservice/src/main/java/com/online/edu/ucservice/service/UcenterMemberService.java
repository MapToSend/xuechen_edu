package com.online.edu.ucservice.service;

import com.online.edu.ucservice.bean.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer queryUcenter(String date);
}
