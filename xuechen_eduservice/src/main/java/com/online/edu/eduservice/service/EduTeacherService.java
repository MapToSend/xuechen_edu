package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.query.QueryData;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-11-27
 */
public interface EduTeacherService extends IService<EduTeacher> {


    Page<EduTeacher> queryConditionPage(Long page, Long limit, QueryData queryTeacher) ;

    List<EduTeacher> queryTeacher();


}
