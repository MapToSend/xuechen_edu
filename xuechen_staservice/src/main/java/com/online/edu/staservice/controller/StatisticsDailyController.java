package com.online.edu.staservice.controller;

import com.online.common.R;
import com.online.edu.staservice.bean.StatisticsDaily;
import com.online.edu.staservice.client.UcService;
import com.online.edu.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private UcService ucService;

    @Autowired
    private StatisticsDailyService dailyService;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/queryDaily")
    public R queryDaily(@RequestParam("type")String type,
                        @RequestParam(value = "begin")String begin,
                        @RequestParam("end")String end) {

      Map<String, ArrayList<String>>  Map=dailyService.queryDaily(type,begin,end);



        return R.ok().data("nums",Map.get("nums")).data("dates",Map.get("dates"));

    }

    /**
     * 自动0点自动存储 每天注册人数  会员登录人数  视频播放量 课程数
     * //    @Scheduled(cron = "0 0/3 0 * * MON-SAT")
     * //    @Scheduled(cron = "0 0 * * * MON-SAT")
     *
     * @return
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public R saveDaily() {

        //1.2获取日期格式对象
        String str_date = simpleDateFormat.format(new Date());

        System.out.println(str_date);
        //获取每天的注册人数
        R r = ucService.queryUcenter(str_date);
        Integer count = (Integer) r.getData().get("count");

//        //删除已存在的统计对象
//        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
//        dayQueryWrapper.eq("date_calculated", day);
//        dailyService.remove(dayQueryWrapper);

        //获取统计信息

        //登录记录数
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO

        //视频播放数
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        //每日新增课程数
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(count);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(str_date);
        daily.setGmtCreate(new Date());
        daily.setGmtModified(new Date());

        dailyService.save(daily);

        return R.ok();
    }

}

