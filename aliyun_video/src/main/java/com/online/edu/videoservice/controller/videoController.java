package com.online.edu.videoservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.common.R;
import com.online.edu.videoservice.config.OssConfigPerproties;
import com.online.edu.videoservice.service.VideoService;
import com.online.edu.videoservice.utils.AliyunVodSDKUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videoService/vod")
@CrossOrigin
public class videoController {


    @Autowired
    private VideoService videoService;

    /**
     * 获取视频凭证
     * @param
     * @return
     */
    @GetMapping("get/{videoId}")
    public R get(@PathVariable("videoId")String videoId){

        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = null;
        try {
            client = AliyunVodSDKUtils.initVodClient(OssConfigPerproties.accessKeyId, OssConfigPerproties.accessKeySecret);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        String playAuth=null;
        try {

            //设置请求参数
            request.setVideoId(videoId);
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
             playAuth = response.getPlayAuth();
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }


        return R.ok().data("playAuth",playAuth);
    }


    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @GetMapping("/delete/{videoId}")
    public R delete(@PathVariable("videoId")String videoId){

       Boolean falg=videoService.deleteVideo(videoId);


        return falg ? R.ok().data("success","成功"):R.error().data("error","失败");
    }

    /**
     * 上传视频
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R uploadVideo(MultipartFile file){

        String videoId = videoService.uploadVideo(file);

        return R.ok().data("videoId",videoId);
    }
}
