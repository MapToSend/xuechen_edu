package com.online.edu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.common.R;
import com.online.edu.eduservice.bean.*;
import com.online.edu.eduservice.bean.query.SubjectSelect;
import com.online.edu.eduservice.bean.query.SubjectSelect_2;
import com.online.edu.eduservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/frontCourse")
public class FrontCourseController {

    @Autowired
    private EduCourseService EduCourseService;

    @Autowired
    private EduChapterService EduChapterService;
    @Autowired
    private EduCourseDescriptionService EduCourseDescriptionService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduSubjectService eduSubjectService;

    @Autowired
    private EduVideoService eduVideoService;

    @GetMapping("/query/{courseId}")
    public R queryCourse(@PathVariable("courseId") String courseId) {

        //课程信息
        EduCourse eduCourse = EduCourseService.getById(courseId);

        //课程简介信息
        EduCourseDescription eduCourseDescription = EduCourseDescriptionService.getById(courseId);

        //课程老师信息
        EduTeacher byId = null;
        if (!StringUtils.isEmpty(eduCourse.getTeacherId())) {

            byId = eduTeacherService.getById(eduCourse.getTeacherId());
        }

        //课程所属分类
        EduSubject eduSubject = null;
        EduSubject eduSubject_parent = null;
        if (!StringUtils.isEmpty(eduCourse.getSubjectId())) {
            eduSubject = eduSubjectService.getById(eduCourse.getSubjectId());
            eduSubject_parent = eduSubjectService.getById(eduSubject.getParentId());
        }

        //课程章节
        QueryWrapper<EduChapter> eduChapter = new QueryWrapper<>();
        eduChapter.eq("course_id", courseId);

        List<EduChapter> list = EduChapterService.list(eduChapter);

        ArrayList<SubjectSelect> arrayList = new ArrayList<>();

        for (EduChapter chapter : list) {

            QueryWrapper<EduVideo> eduVideo = new QueryWrapper<>();
            eduVideo.eq("chapter_id", chapter.getId());
            List<EduVideo> videos = eduVideoService.list(eduVideo);

            SubjectSelect subjectSelect = new SubjectSelect();
            subjectSelect.setLabel(chapter.getTitle());
            subjectSelect.setValue(chapter.getId());

            for (EduVideo video : videos) {

                if (chapter.getId().equals(video.getChapterId())) {

                    SubjectSelect_2 subjectSelect_2 = new SubjectSelect_2();
                    subjectSelect_2.setLabel(video.getTitle());
                    subjectSelect_2.setValue(video.getId());
                    subjectSelect_2.setVideoSourceId(video.getVideoSourceId());

                    subjectSelect.getChildren().add(subjectSelect_2);

                }

            }
            arrayList.add(subjectSelect);

        }

        return R.ok().data("course", eduCourse).data("courseDesc", eduCourseDescription).data("teacher", byId).data("eduSubject", eduSubject).
                data("eduSubject_parent", eduSubject_parent).data("videos", arrayList);
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

        Page<EduCourse> eduCourse = new Page<>(page, pagesize);

        EduCourseService.page(eduCourse, null);

        return R.ok().data("teachers", eduCourse).data("nextPage", eduCourse.hasNext()).data("lastPage", eduCourse.hasPrevious());
    }
}
