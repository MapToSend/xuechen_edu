package com.online.edu.eduservice.controller;

import com.aliyun.oss.OSSClient;
import com.online.common.R;
import com.online.edu.eduservice.config.OssConfigPerproties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/eduservice")
@CrossOrigin
public class OssController {

    /**
     * 讲师头像上传
     *
     * @param file
     * @return
     */
        @PostMapping("/oss")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "token",required = false,defaultValue = "0")String token) {

        //获取文件名和文件输入流
        String filename = file.getOriginalFilename();

        //解决图片重复性问题
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 15);
        filename = uuid + "_" + filename;

        //每天存储文件
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String str_date = simpleDateFormat.format(date);


        if ("1".equals(token)){
            //课程首页图片存储
            filename = str_date + "/courseImg/" + filename;
        }else {
            filename = str_date + "/teacherImg/" + filename;
        }





        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OssConfigPerproties.endpoint;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = OssConfigPerproties.accessKeyId;
        String accessKeySecret = OssConfigPerproties.accessKeySecret;
        String bucketName = OssConfigPerproties.bucketName;

        // 创建OSSClient实例。存储文件到OSS中bucketName
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filename, in);
        ossClient.shutdown();

        //https://xuecheng-ossimg.oss-cn-beijing.aliyuncs.com/jquery%E5%B8%B8%E7%94%A8%E6%96%B9%E6%B3%95.txt
        String url = "https://" + bucketName + "." + endpoint + "/" + filename;

        return R.ok().data("url", url);
    }
}
