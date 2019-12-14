package com.online.edu.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.staservice.bean.StatisticsDaily;
import com.online.edu.staservice.mapper.StatisticsDailyMapper;
import com.online.edu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private StatisticsDailyMapper statisticsDailyMapper;

    @Override
    public Map<String, ArrayList<String>> queryDaily(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();

        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);

        List<StatisticsDaily> statisticsDailies = statisticsDailyMapper.selectList(wrapper);

        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> nums = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : statisticsDailies) {

            dates.add(statisticsDaily.getDateCalculated());

            switch (type) {
                case "register_num":
                    nums.add(statisticsDaily.getRegisterNum().toString());
                    break;
                case "login_num":
                    nums.add(statisticsDaily.getLoginNum().toString());
                    break;
                case "video_view_num":
                    nums.add(statisticsDaily.getVideoViewNum().toString());
                    break;
                case "course_num":
                    nums.add(statisticsDaily.getCourseNum().toString());
                    break;
            }

        }

        Map<String, ArrayList<String>> stringArrayListMap = new HashMap<>();

        stringArrayListMap.put("dates", dates);
        stringArrayListMap.put("nums", nums);

        return stringArrayListMap;

    }
}
