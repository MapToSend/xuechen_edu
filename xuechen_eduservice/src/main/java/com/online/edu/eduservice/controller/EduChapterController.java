package com.online.edu.eduservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.common.R;
import com.online.common.RedisSetTime;
import com.online.edu.eduservice.bean.EduSubject;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.query.Chapter;
import com.online.edu.eduservice.bean.query.CourseInfo;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseService;
import com.online.edu.eduservice.service.EduSubjectService;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduSubjectService subjectService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;
    /**
     * 全体课程数据存储
     *
     * @param redis_key
     * @return
     */
    @GetMapping("/save")
    @Transactional
    public R saveChapterInfo(@RequestParam("redis_key") String redis_key) {

        try {


            String s = redis_key.split("_")[0];
            String s1 = stringRedisTemplate.opsForValue().get(s);
            CourseInfo courseInfo = objectMapper.readValue(s1, CourseInfo.class);

            //课程基本信息存储
            String courseId = eduCourseService.saveCourseInfo(courseInfo);

            //课程章节存储
            String s2 = stringRedisTemplate.opsForValue().get(redis_key);
            List list = objectMapper.readValue(s2, List.class);

            eduChapterService.saveChapter(list,courseId);

            //数据存储成功 清除redis的数据
            stringRedisTemplate.delete(redis_key);
            stringRedisTemplate.delete(s);


        } catch (IOException e) {
           return R.ok().data("error","课程已存在!!!");
        }

        return R.ok();
    }

    /**
     * 获取展示信息
     *
     * @param redis_key
     * @return
     */
    @GetMapping("/get")
    public R getChapterInfo(@RequestParam("redis_key") String redis_key) {

        CourseInfo courseInfo = null;
        String courseId = null;
        try {
            String chapterInfo = stringRedisTemplate.opsForValue().get(redis_key);

            courseInfo = objectMapper.readValue(chapterInfo, CourseInfo.class);
            courseId = courseInfo.getSubject().get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置老师名
        EduTeacher byId = teacherService.getById(courseInfo.getTeacher());
        courseInfo.setTeacher(byId.getName());

        //设置分类
        List<String> subject1 = courseInfo.getSubject();

        EduSubject subject = subjectService.getById(courseInfo.getSubject().get(0));

        EduSubject subject_1 = subjectService.getById(courseInfo.getSubject().get(1));
        subject1.remove(0);
        subject1.remove(0);
        subject1.add(subject.getTitle());
        subject1.add(subject_1.getTitle());

        return R.ok().data("chapterInfo", courseInfo).data("courseId", courseId);
    }

    /**
     * 更新章节数据
     *
     * @param redis_key
     * @param chapter
     * @return
     */
    @PostMapping("/update")
    public R updateChapter(@RequestParam("redis_key") String redis_key, Chapter chapter) {

        String s = stringRedisTemplate.opsForValue().get(redis_key);
        if (s != null) {

            try {
                List list = objectMapper.readValue(s, List.class);

                for (int i = 0; i < list.size(); i++) {

                    Map<String, String> map = (Map<String, String>) list.get(i);

                    if (map.get("id").equals(chapter.getId())) {

                        map.remove("title");
                        map.remove("sort");

                        map.put("title", chapter.getTitle());
                        map.put("sort", chapter.getSort());
                    }

                }

                String s1 = objectMapper.writeValueAsString(list);
                stringRedisTemplate.opsForValue().set(redis_key, s1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List chapter1 = this.getChapter(redis_key);

        return R.ok().data("redis_key", redis_key).data("chapterList", chapter1);
    }

    /**
     * 删除课程章节
     *
     * @param chapterId
     * @return
     */
    @PostMapping("/delete")
    public R deleteChapter(@RequestParam("redis_key") String redis_key,
                           @RequestParam("chapterId") String chapterId) {
        List list = null;
        try {
            String s = stringRedisTemplate.opsForValue().get(redis_key);

            list = objectMapper.readValue(s, List.class);

            for (int i = 0; i < list.size(); i++) {
                Class<?> aClass = list.get(i).getClass();
                Map<String, String> o = (Map<String, String>) list.get(i);

                if (chapterId.equals(o.get("id"))) {

                    list.remove(o);
                }

            }

            String s1 = objectMapper.writeValueAsString(list);

            stringRedisTemplate.opsForValue().set(redis_key, s1, RedisSetTime.TIME, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok().data("chapterList", list);
    }

    /**
     * 获取redis中的数据
     *
     * @param redis_key
     * @return
     */
    private List getChapter(String redis_key) {

        String s = stringRedisTemplate.opsForValue().get(redis_key);
        //排序后的数据
        List<Chapter> chapters = null;
        try {
            if (s != null) {

                List list = objectMapper.readValue(s, List.class);

                chapters = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    LinkedHashMap<String, Object> o1 = (LinkedHashMap<String, Object>) list.get(i);
                    Chapter target = new Chapter();
                    target.setId((String) o1.get("id"));
                    target.setTitle((String) o1.get("title"));
                    target.setSort((String) o1.get("sort"));
                    target.setChapters((List<Chapter>) o1.get("chapters"));
                    chapters.add(target);
                }

                Collections.sort(chapters);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO 小节排序

//        //小节排序
//        for (int i = 0; i < chapters.size(); i++) {
//            List<Chapter> o2=chapters.get(i).getChapters();
//
//            for (Object obj : o2) {
//
//                Map<String, Object> o1 = (Map<String, Object>)obj;
//
//                Chapter target = new Chapter();
//                target.setId((String) o1.get("id"));
//                target.setTitle((String) o1.get("title"));
//                target.setSort((String) o1.get("sort"));
//                target.setChapters((List<Chapter>) o1.get("chapters"));
//                List<Chapter> objects = new ArrayList<>();
//                objects.add(target);
//                chapters.get(i).setChapters(objects);
//
//            }
//
//
//
//            if (chapters.get(i).getChapters() != null) {
//
//                Collections.sort(chapters.get(i).getChapters());
//            }
//
//        }

        return chapters;
    }

    /**
     * 获取章节数据
     *
     * @param redis_key
     * @return
     */
    @GetMapping("/getChapter")
    public R getchapter_web(@RequestParam("redis_key") String redis_key) {

        List chapter = this.getChapter(redis_key);
        return R.ok().data("chapterList", chapter);

    }

    /**
     * 存储数据到redis中
     *
     * @param redis_key
     * @param chapter
     * @return
     */
    @PostMapping("/add")
    public R addChapter(@RequestParam("redis_key") String redis_key, Chapter chapter) {

        String chapterId = UUID.randomUUID().toString().replace("-", "");
        chapter.setId(chapterId);

        if (StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(redis_key))) {
            //新课程存储
            List<Chapter> chapters = new ArrayList<>();

            chapters.add(chapter);

            try {
                String s = objectMapper.writeValueAsString(chapters);
                stringRedisTemplate.opsForValue().set(redis_key, s, RedisSetTime.TIME, TimeUnit.SECONDS);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else {
            //已存在课程，新添加章节
            String s = stringRedisTemplate.opsForValue().get(redis_key);
            try {
                List list = objectMapper.readValue(s, List.class);
                list.add(chapter);

                String chapterList = objectMapper.writeValueAsString(list);

                stringRedisTemplate.opsForValue().set(redis_key, chapterList, RedisSetTime.TIME, TimeUnit.SECONDS);
//             /   System.out.println(list + "+++++++++++++++++++++++++++++++++++++++++++++++===");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //获取redis中临时数据
        List chapter1 = this.getChapter(redis_key);

        return R.ok().data("redis_key", redis_key).data("chapterList", chapter1);
    }

    /**
     * 获取章节分类id
     */

    @GetMapping("/getSubject")
    public R getSubject(String redis_key) {

        String s = stringRedisTemplate.opsForValue().get(redis_key);
        String s1 = null;
        try {

            CourseInfo courseInfo = objectMapper.readValue(s, CourseInfo.class);
            s1 = courseInfo.getSubject().get(1);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return R.ok().data("subjectId", s1);
    }

}

