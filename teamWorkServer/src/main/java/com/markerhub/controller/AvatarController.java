package com.markerhub.controller;
import com.markerhub.common.lang.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
public class AvatarController {
    @PostMapping("/uploadImage")
    public Result upLoadImage(MultipartFile file){
        System.out.println("!!!上传头像");
        if (file.isEmpty()){
            return  Result.fail("上传失败，请选择文件");
        }
        System.out.println(file.getOriginalFilename());
        String filename = "abc";
        File dest = new File("D:\\Avatar\\"+file.getOriginalFilename());
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            System.out.println("！！错误");
            e.printStackTrace();
        }

        String rootFilePath = System.getProperty("user.dir") + "/files/" + "_" ;
        System.out.println(rootFilePath);
        return Result.succ("success");
    }
}