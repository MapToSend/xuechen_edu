package com.online.edu.ucservice.controller;


import com.online.common.R;
import com.online.edu.ucservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
@RestController
@CrossOrigin
@RequestMapping("/ucservice/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/queryMember/{day}")
    public R queryUcenter(@PathVariable("day") String day){

      Integer count= ucenterMemberService.queryUcenter(day);


        return R.ok().data("count",count);
    }

}

