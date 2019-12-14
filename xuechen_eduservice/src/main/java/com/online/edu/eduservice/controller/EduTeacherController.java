package com.online.edu.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.common.R;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.query.QueryData;
import com.online.edu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin//跨域：协议不同：端口号不同 ip不同： nginx解决跨域,CrossOrigin注解解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 登录
     * @return
     */
//    {"code":20000,"data":{"token":"admin"}}
    @PostMapping("/login")
    public R login(){

        System.out.println("hahah");
        return R.ok().data("token","admin");
    }
//        {"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}

    /**
     * 用户登录信息获取
     * @return
     */
    @PostMapping("/info")
    public R info(){
        return R.ok().data("roles","['admin']").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }











    /**
     * 更新老师
     */
    @PostMapping("/update")
    public R updataEduTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean b = teacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }
    /**
     * 保存老师
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("/save")
    public R saveEduTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean b = teacherService.save(eduTeacher);

        return b ? R.ok() : R.error();

    }

    /**
     * 多条件分配查询
     * json参数接受数据 返回json
     *
     * @param page
     * @param limit
     * @param queryTeacher
     * @return
     */
    @PostMapping("/queryCondition/{page}/{limit}")
    public R queryConditionPage(@PathVariable("page") Long page,
                                @PathVariable("limit") Long limit,
                                @RequestBody(required = false) QueryData queryTeacher) {

        System.out.println(queryTeacher);
        Page<EduTeacher> teacherPage = teacherService.queryConditionPage(page, limit, queryTeacher);

        long total = teacherPage.getTotal();

        List<EduTeacher> records = teacherPage.getRecords();

        return R.ok().data("total", total).data("teachers", records);

    }

    /**
     * 简单分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/query/{page}/{limit}")
    public R queryPageTeacherList(@PathVariable("page") Long page,
                                  @PathVariable("limit") Long limit) {

        //分页插件对象
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        teacherService.page(teacherPage, null);

        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        return R.ok().data("total", total).data("teachers", records);
    }

    /**
     * 查询老师列表
     *
     * @return
     */
    @GetMapping("/query")
    public R queryTeacher() {
        List<EduTeacher> list = teacherService.list(null);

        return R.ok().data("teacher", list);

    }

    /**
     * 逻辑删除老师
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public R deleteTeacher(@PathVariable("id") String id) {

        boolean b = teacherService.removeById(id);
        System.out.println(b);
        return b ? R.ok() : R.error();

    }

    /**
     *
     */
    @GetMapping("/query/{id}")
    public  R queryTeacherByid(@PathVariable("id")Long id){

        EduTeacher eduTeacher = teacherService.getById(id);

        return R.ok().data("tea",eduTeacher);

    }
}


