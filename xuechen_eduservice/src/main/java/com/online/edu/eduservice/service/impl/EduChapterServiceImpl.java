package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.bean.EduChapter;
import com.online.edu.eduservice.bean.EduVideo;
import com.online.edu.eduservice.mapper.EduChapterMapper;
import com.online.edu.eduservice.mapper.EduVideoMapper;
import com.online.edu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;
    //储存章节信息
    @Override
    public void saveChapter(List list, String cuorseId) {

        for (Object o : list) {

            //课程章节存储
            Map<String, Object> map = (Map<String, Object>) o;

            EduChapter chapter = new EduChapter();
            chapter.setCourseId(cuorseId);
            chapter.setTitle((String) map.get("title"));
            chapter.setSort(Integer.parseInt((String) map.get("sort")));

            eduChapterMapper.insert(chapter);


            //课程小结存储
            List videos = (List) map.get("chapters");


            for (Object video : videos) {
                Map<String, Object> map_1 = (Map<String, Object>) video;

                EduVideo eduVideo = new EduVideo();
                eduVideo.setCourseId(cuorseId);
                eduVideo.setChapterId(chapter.getId());
                eduVideo.setTitle((String) map_1.get("title"));
                eduVideo.setSort(Integer.parseInt((String) map_1.get("sort")));
                eduVideo.setVideoSourceId((String) map_1.get("videoId"));
                eduVideoMapper.insert(eduVideo);
            }


        }

    }
}
