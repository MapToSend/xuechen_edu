package com.online.edu.eduservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.common.R;
import com.online.common.RedisSetTime;
import com.online.edu.eduservice.bean.query.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 删除小节
     *
     * @param redis_key
     * @param videoId
     * @return
     */
    @GetMapping("/delete")
    public R deleteVideo(@RequestParam("redis_key") String redis_key,
                         @RequestParam("videoId") String videoId) {

        String s = stringRedisTemplate.opsForValue().get(redis_key);
        List list = null;
        try {
            list = objectMapper.readValue(s, List.class);

            for (Object o : list) {
                Map<String, Object> map = (Map<String, Object>) o;

                List chapters = (List) map.get("chapters");

                for (Object chapter : chapters) {
                    Map<String, Object> videos = (Map<String, Object>) chapter;

                    if (videoId.equals(videos.get("id"))) {
                        chapters.remove(chapter);
                        break;
                    }

                }

            }
            String s1 = objectMapper.writeValueAsString(list);

            stringRedisTemplate.opsForValue().set(redis_key, s1, RedisSetTime.TIME, TimeUnit.SECONDS);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return R.ok().data("chapterList", list);
    }

    /**
     * 更新小节
     *
     * @param chapter
     * @param redis_key
     * @param chapterId
     * @return
     */
    @PostMapping("/update")
    public R updateVideo(@RequestBody Chapter chapter,
                         @RequestParam("redis_key") String redis_key,
                         @RequestParam("chapterId") String chapterId) {

        String s = stringRedisTemplate.opsForValue().get(redis_key);

        List list = null;
        try {
            list = objectMapper.readValue(s, List.class);
            for (Object o : list) {

                Map<String, Object> map = (Map<String, Object>) o;

                if (chapterId.equals(map.get("id"))) {
                    List chapters = (List) map.get("chapters");

                    for (Object o2 : chapters) {
                        Map<String, Object> map1 = (Map<String, Object>) o2;

                        if (map1.get("title").equals(chapter.getTitle())&&map1.get("sort").equals(chapter.getSort())&&map1.get("fileName").equals(chapter.getFileName())&&map1.get("videoId").equals(chapter.getVideoId())) {
                            return R.ok().data("info", list).data("error", "课程小节以存在！！！！");
                        }

                    }


                    for (Object chapter1 : chapters) {

                        Map<String, Object> map_1 = (Map<String, Object>) chapter1;

                        if (chapter.getId().equals(map_1.get("id"))) {
                            map_1.put("title", chapter.getTitle());
                            map_1.put("sort", chapter.getSort());
                            map_1.put("videoId", chapter.getVideoId());
                            map_1.put("fileName", chapter.getFileName());
                            break;
                        }

                    }

                }
            }

            String s1 = objectMapper.writeValueAsString(list);
            stringRedisTemplate.opsForValue().set(redis_key,s1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().data("info", list);
    }

    /**
     * 新增小节
     *
     * @param chapter
     * @param redis_key
     * @param chapterId
     * @return
     */
    @PostMapping("/add")
    public R addVideo(@RequestBody Chapter chapter,
                      @RequestParam("redis_key") String redis_key,
                      @RequestParam("chapterId") String chapterId) {
        String videoId = UUID.randomUUID().toString().replace("-", "");

        String s = stringRedisTemplate.opsForValue().get(redis_key);
        List list = null;
        try {
            list = objectMapper.readValue(s, List.class);

            for (int i = 0; i < list.size(); i++) {

                Map<String, Object> map = (Map<String, Object>) list.get(i);

                ArrayList title = (ArrayList) map.get("chapters");

                for (Object o : title) {
                    Map<String, Object> map1 = (Map<String, Object>) o;

                    if (map1.get("title").equals(chapter.getTitle())) {
                        return R.ok().data("error", "课程小节以存在！！！！");
                    }

                }
//
//                if (chapter.getTitle().equals(title1)){
//
//                    return R.ok().data("error","课程小节以存在！！！！");
//                }

                if (map.get("id").equals(chapterId)) {

                    ArrayList<Chapter> list_1 = (ArrayList<Chapter>) map.get("chapters");

                    chapter.setId(videoId);
                    list_1.add(chapter);
                }

            }

            String s1 = objectMapper.writeValueAsString(list);

            stringRedisTemplate.opsForValue().set(redis_key, s1, RedisSetTime.TIME, TimeUnit.SECONDS);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().data("info", list);
    }

}

