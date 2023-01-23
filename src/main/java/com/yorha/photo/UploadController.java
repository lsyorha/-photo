package com.yorha.photo;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController //封装
public class UploadController {
    @PostMapping("/upload") //访问路径
    //上传文件，访问路径
    public String upload(MultipartFile file){

        //效应文件类型及大小
        if (file.isEmpty()){
            return "图片上传失败";
        }
        //解决图片名重复问题
        String originalFilename = file.getOriginalFilename();//获取原来的图片名称
/*        String[] arr = originalFilename.split("\\.");
        String ext = "." + arr[arr.length-1]; //重命名图片名，且避免图片出现很多.的情况
        String uid = UUID.randomUUID().toString().replace("-","");
        String fileName = uid + ext;*/
        String arr = originalFilename.substring(originalFilename.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MM月dd日HH时mm分ss秒");
        String timeStamp=simpleDateFormat.format(new Date());
        String fileName = timeStamp + arr;
//        上传图片
        //获取路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
/*        System.out.println("-------------------------------");
        System.out.println(applicationHome);*/
        String pre =applicationHome.getDir().getParentFile().getParentFile() + "\\src\\main\\resources\\image\\"; //获取程序运行路径
/*        System.out.println("-------------------------------");
        System.out.println(pre);*/
        String path = pre +fileName;
//        传入路径
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
