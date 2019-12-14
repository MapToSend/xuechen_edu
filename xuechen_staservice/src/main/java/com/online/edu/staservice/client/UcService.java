package com.online.edu.staservice.client;

import com.online.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("xuechen-usService")
@Component
public interface UcService {

    @GetMapping("/ucservice/member/queryMember/{day}")
    public R queryUcenter(@PathVariable("day") String day);
}
