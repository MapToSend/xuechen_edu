package com.online.edu.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.common.R;
import com.online.edu.eduservice.bean.EduSubject;
import com.online.edu.eduservice.bean.query.TreeChildren;
import com.online.edu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-11-30
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @GetMapping("/save")
    public R saveSubject(@RequestParam("title")String title,
                          @RequestParam(value = "parent_title",required = false) String parent_title ){

        if (StringUtils.isEmpty(parent_title)){
            //1级分类添加
            QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("title",title);
            EduSubject subject = subjectService.getOne(queryWrapper);

            if (subject!=null){

                return R.error().data("errorInfo","该课程以存在！！！");
            }
            EduSubject subject_1 = new EduSubject();
            subject_1.setParentId("0");
            subject_1.setSort(0);
            subject_1.setTitle(title);

            boolean falg = subjectService.save(subject_1);


            return falg ? R.ok().data("info","添加成功"):R.error().data("errorInfo","添加失败！！！");
        }else{
            //二级分类添加

            Boolean aBoolean = this.twoSubject(title, parent_title);


            return aBoolean? R.ok().data("info","添加成功"):R.error().data("errorInfo","该课程已存在！！！");
        }

    }

    /**
     * 二级分类添加
     * @param title
     * @param parent_title
     * @return
     */
    private Boolean twoSubject(String title,String parent_title ){

        boolean falg = false;
        try {
            QueryWrapper<EduSubject> objectQueryWrapper = new QueryWrapper<>();

            objectQueryWrapper.eq("title",parent_title);

            EduSubject subject = subjectService.getOne(objectQueryWrapper);

            EduSubject subject_1 = new EduSubject();
            subject_1.setParentId(subject.getId());
            subject_1.setSort(0);
            subject_1.setTitle(title);

            falg = subjectService.save(subject_1);
        } catch (Exception e) {
            return false;
        }

        return falg;
    }


    @GetMapping("/delete")
    public R deleteSubject(@RequestParam("title")String title){


       Boolean falg =subjectService.deleteSub(title);

        return falg ?R.ok().data("success","删除成功"):R.error().data("error","删除失败");
    }



    /**
     * 课程文件添加功能
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R uploadSubJect(MultipartFile file) {

        Boolean falg = subjectService.uploadSubject(file);

        return falg ?R.ok().data("success","文件上传成功！！！"):R.error().data("error","表格数据不能为空！！！");

    }

    @GetMapping("list")
    public R querySubjectList(){

        List<TreeChildren> list = subjectService.list();

        return R.ok().data("subjectLists",list);

    }

}

