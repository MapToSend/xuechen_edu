package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.query.QueryData;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.mapper.EduTeacherMapper;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-11-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Autowired
    private EduSubjectMapper subjectMapper;

    /**
     * 查询讲师名
     *
     * @return
     */
    @Override
    public List<EduTeacher> queryTeacher() {

        List<EduTeacher> eduTeachers = eduTeacherMapper.selectList(null);

        return eduTeachers;
    }



    /***
     *
     *多条件分页查询teacher
     */
    @Override
    public Page<EduTeacher> queryConditionPage(Long page, Long limit, QueryData queryTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (queryTeacher != null) {
            if (!StringUtils.isEmpty(queryTeacher.getName())) {
                wrapper.like("name", queryTeacher.getName());
            }

            if (!StringUtils.isEmpty(queryTeacher.getLevel())) {
                wrapper.eq("level", queryTeacher.getLevel());

             }

            if (!StringUtils.isEmpty(queryTeacher.getBegin()) && !StringUtils.isEmpty(queryTeacher.getEnd())) {

                wrapper.between("gmt_create", queryTeacher.getBegin(), queryTeacher.getEnd());
            }
        }

        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        this.page(teacherPage, wrapper);

        return teacherPage;
    }

}
