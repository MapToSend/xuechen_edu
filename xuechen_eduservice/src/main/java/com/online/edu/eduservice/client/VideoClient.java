package com.online.edu.eduservice.client;

import com.online.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("xuechen-aliyunVideo")
@Component
public interface VideoClient {

    /**
     * evideo微服务的方法
     * @param videoId
     * @return
     */
    @GetMapping("/videoService/vod/delete/{videoId}")
    public R delete(@PathVariable("videoId")String videoId);
}
