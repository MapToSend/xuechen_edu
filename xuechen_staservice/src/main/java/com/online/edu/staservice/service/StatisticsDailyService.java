package com.online.edu.staservice.service;

import com.online.edu.staservice.bean.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 向长城
 * @since 2019-12-10
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    Map<String, ArrayList<String>> queryDaily(String type, String begin, String end);
}
