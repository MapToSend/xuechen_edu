package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.bean.EduCourse;
import com.online.edu.eduservice.bean.query.CourseInfo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-02
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfo courseInfo);

    Page<EduCourse> queryConditionPage(Long page, Long limit, String title);
}
