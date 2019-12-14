package com.online.edu.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.common.R;
import com.online.common.RedisSetTime;
import com.online.edu.eduservice.bean.EduChapter;
import com.online.edu.eduservice.bean.EduCourse;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.EduVideo;
import com.online.edu.eduservice.bean.query.CouresSelect;
import com.online.edu.eduservice.bean.query.CourseInfo;
import com.online.edu.eduservice.bean.query.SubjectSelect;
import com.online.edu.eduservice.client.VideoClient;
import com.online.edu.eduservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-12-02
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduSubjectService subjectService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VideoClient videoClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 删除课程
     */

    @GetMapping("/delete/{courseId}")
    @Transactional
    public R deleteCourse(@PathVariable("courseId") String courseId) {

        try {
            //课程基本信息删除
            eduCourseService.removeById(courseId);

            //课程简介删除
            eduCourseDescriptionService.removeById(courseId);

            //课程章节删除
            QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();

            chapterQueryWrapper.eq("course_id", courseId);

            eduChapterService.remove(chapterQueryWrapper);

            //课程小结删除
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();

            videoQueryWrapper.eq("course_id", courseId);

            List<EduVideo> list = eduVideoService.list(videoQueryWrapper);

            for (EduVideo eduVideo : list) {

                if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {

                    //删除阿里云视频
                    videoClient.delete(eduVideo.getVideoSourceId());

                }

            }

            //删除
            eduVideoService.remove(videoQueryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
           R.error().data("error","删除失败");
        }

        return R.ok();
    }

    /**
     * 多条件分配查询
     * json参数接受数据 返回json
     *
     * @param page
     * @param limit
     * @param queryconditon
     * @return
     */
    @PostMapping("/queryCondition/{page}/{limit}")
    public R queryConditionPage(@PathVariable("page") Long page,
                                @PathVariable("limit") Long limit,
                                @RequestParam(value = "queryconditon", required = false) String queryconditon) {

        Page<EduCourse> teacherPage = eduCourseService.queryConditionPage(page, limit, queryconditon);

        long total = teacherPage.getTotal();

        List<EduCourse> records = teacherPage.getRecords();

        List<EduTeacher> eduCourseDescriptions = new ArrayList<>();
        for (EduCourse record : records) {
            EduTeacher byId = teacherService.getById(record.getTeacherId());
            eduCourseDescriptions.add(byId);
        }

        return R.ok().data("total", total).data("course", records).data("teacher", eduCourseDescriptions);

    }

    //*********************redis存储********************************

    /**
     * 更新课程基本信息
     */
    @PostMapping("/update")
    public R updateCourse(@RequestBody CourseInfo courseInfo, @RequestParam("id") String id) {

        try {
            String s = objectMapper.writeValueAsString(courseInfo);

            stringRedisTemplate.opsForValue().set(id, s, RedisSetTime.TIME, TimeUnit.SECONDS);

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }

        return R.ok().data("redis_key", id);
    }

    /**
     * 从redis获取课程信息回显
     *
     * @param redis_key
     * @return
     */
    @PostMapping("/getCourse")
    public R getCourse(String redis_key) {

//        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        String info = stringRedisTemplate.opsForValue().get(redis_key);
        CourseInfo courseInfo = null;
        try {
            courseInfo = objectMapper.readValue(info, CourseInfo.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().data("courseInfo", courseInfo);
    }

    /**
     * 存储课程基本信息到redis中
     *
     * @param courseInfo
     * @return
     */
    @PostMapping("/saveCourse")
    public R saveCourse(@RequestBody CourseInfo courseInfo) {
        System.out.println(courseInfo);
        String redis_key = UUID.randomUUID().toString().replace("-", "");

        try {
            String info = objectMapper.writeValueAsString(courseInfo);
            stringRedisTemplate.opsForValue().set(redis_key, info, RedisSetTime.TIME, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return R.ok().data("redis_key", redis_key);
    }

    /**
     * 下拉列表分类查询
     *
     * @return
     */
    @GetMapping("/querySubject")
    public R querySubject() {

        List<SubjectSelect> subjectSelects = subjectService.querySubject();
        System.out.println(subjectSelects);
        return R.ok().data("subjects", subjectSelects);
    }

    /**
     * 下拉列表讲师名查询
     *
     * @return
     */
    @GetMapping("/queryTeacher")
    public R queryTeacher() {

        List<EduTeacher> queryTeacher = teacherService.queryTeacher();

        List<CouresSelect> couresSelects = new ArrayList<>();

        for (int i = 0; i < queryTeacher.size(); i++) {

            EduTeacher eduTeacher = queryTeacher.get(i);

            CouresSelect couresSelect = new CouresSelect("选项" + (i + 1), eduTeacher.getName());

            couresSelect.setTeacherId(eduTeacher.getId());
            couresSelects.add(couresSelect);

        }

        return R.ok().data("teacherInfo", couresSelects);
    }

}

