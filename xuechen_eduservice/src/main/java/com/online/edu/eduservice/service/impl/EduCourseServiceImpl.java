package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.bean.EduCourse;
import com.online.edu.eduservice.bean.EduCourseDescription;
import com.online.edu.eduservice.bean.query.CourseInfo;
import com.online.edu.eduservice.mapper.EduCourseDescriptionMapper;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduCourseDescriptionMapper eduCourseDescriptionMapper;

    /**
     * 保存用户基本信息
     *
     * @param courseInfo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {

        EduCourse eduCourse = new EduCourse();

        eduCourse.setTeacherId(courseInfo.getTeacher());
        eduCourse.setSubjectId(courseInfo.getSubject().get(1));
        eduCourse.setTitle(courseInfo.getTitle());
        eduCourse.setPrice(courseInfo.getPrice());
        eduCourse.setLessonNum(courseInfo.getLessonNum());
        eduCourse.setCover(courseInfo.getCover());

        eduCourse.setStatus("Normal");
        eduCourseMapper.insert(eduCourse);

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescriptionMapper.insert(eduCourseDescription);

        return eduCourse.getId();
    }

    //分页条件数据查询
    @Override
    public Page<EduCourse> queryConditionPage(Long page, Long limit, String title) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(title)) {

            wrapper.like("title",title);
        }

        Page<EduCourse> objectPage = new Page<>(page,limit);

        this.page(objectPage, wrapper);



        return objectPage;
    }
}
