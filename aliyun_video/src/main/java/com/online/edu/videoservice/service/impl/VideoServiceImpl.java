package com.online.edu.videoservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.online.edu.videoservice.config.OssConfigPerproties;
import com.online.edu.videoservice.service.VideoService;
import com.online.edu.videoservice.utils.AliyunVodSDKUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
//@Slf4j
public class VideoServiceImpl implements VideoService {

    /**
     * 视频上传阿里云
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(OssConfigPerproties.accessKeyId, OssConfigPerproties.accessKeySecret, title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);

            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID

            String videoId = response.getVideoId();
//			if (response.isSuccess()) {
//				System.out.print("VideoId=" + response.getVideoId() + "\n");
//			} else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
////				System.out.print("VideoId=" + response.getVideoId() + "\n");
////				System.out.print("ErrorCode=" + response.getCode() + "\n");
////				System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//			}
            return videoId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    /**
     * 根据id删除
     *
     * @param videoId
     * @return
     */
    @Override
    public Boolean deleteVideo(String videoId) {

        try {

            //获取客户端
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(OssConfigPerproties.accessKeyId, OssConfigPerproties.accessKeySecret);


            //创建删除视频请求对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();

            //设置删除视频id
            deleteVideoRequest.setVideoIds(videoId);

            //客户端发送请求 返回响应对象
            DeleteVideoResponse acsResponse = client.getAcsResponse(deleteVideoRequest);




        } catch (ClientException e) {
            return false;
        }

        return true;
    }
}