package com.online.edu.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.bean.EduSubject;
import com.online.edu.eduservice.bean.query.SubjectSelect;
import com.online.edu.eduservice.bean.query.TreeChildren;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-11-30
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 读取文件存储课程表
     */
    Boolean uploadSubject(MultipartFile file);

    /**
     * 查询所有的课程
     * @return
     */
    List<TreeChildren> list();

    /**
     * 删除课程 节点树
     * @param title
     * @return
     */
    Boolean deleteSub(String title);


    List<SubjectSelect> querySubject();
}
