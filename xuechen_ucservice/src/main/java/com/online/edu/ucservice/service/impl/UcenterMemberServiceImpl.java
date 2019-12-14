package com.online.edu.ucservice.service.impl;

import com.online.edu.ucservice.bean.UcenterMember;
import com.online.edu.ucservice.mapper.UcenterMemberMapper;
import com.online.edu.ucservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private UcenterMemberMapper ucenterMemberMapper;

    @Override
    public Integer queryUcenter(String date) {
        Integer count = ucenterMemberMapper.queryUcenter(date);
        return count;
    }
}
