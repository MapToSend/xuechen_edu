package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.common.R;
import com.online.edu.eduservice.bean.EduCourse;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/frontTeacher")
public class FrontTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("/query/{teacherId}")
    public R queryTeacher(@PathVariable("teacherId") String teacherId) {

        EduTeacher byId = eduTeacherService.getById(teacherId);

        List<EduCourse> list = null;
        if (!StringUtils.isEmpty(byId.getId())) {

            QueryWrapper<EduCourse> EduCourse = new QueryWrapper<>();

            EduCourse.eq("teacher_id", byId.getId());

            list = eduCourseService.list(EduCourse);

        }

        return R.ok().data("teacher", byId).data("course", list);
    }

    /**
     * 分页数据查询
     *
     * @param page
     * @param pagesize
     * @return
     */
    @GetMapping("/query/{page}/{pagesize}")
    public R queryTeacher(@PathVariable("page") Long page, @PathVariable("pagesize") Long pagesize) {

        Page<EduTeacher> EduTeacher = new Page<>(page, pagesize);

        eduTeacherService.page(EduTeacher, null);

        return R.ok().data("teachers", EduTeacher).data("nextPage", EduTeacher.hasNext()).data("lastPage", EduTeacher.hasPrevious());
    }
}
