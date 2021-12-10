package com.markerhub.controller;
import com.markerhub.common.lang.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
public class AvatarController {

    @PostMapping("/uploadImage")
    public Result upLoadImage(@RequestParam (value = "file") MultipartFile file,
                              @RequestParam (value = "userId") Long id ){
        if (file.isEmpty()){
            return  Result.fail("上传失败，请选择文件");
        }
        String filename = "abc";
        File dest = new File("D:/PostImage");
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            System.out.println("！！错误");
            e.printStackTrace();
        }
        return Result.succ("success");
    }
}
