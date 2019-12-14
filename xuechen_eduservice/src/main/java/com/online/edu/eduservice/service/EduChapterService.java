package com.online.edu.eduservice.service;

import com.online.edu.eduservice.bean.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-04
 */
public interface EduChapterService extends IService<EduChapter> {

    void saveChapter(List list,String cuorseId);
}
