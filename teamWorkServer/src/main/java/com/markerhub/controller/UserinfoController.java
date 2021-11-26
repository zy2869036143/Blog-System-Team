package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserinfoController {

    @Autowired
    UserinfoService userinfoService;

    @PostMapping("/renewinfo")
    @ResponseBody
    public Result renewinfo(@RequestBody User user){
        try {
            userinfoService.renewinfo(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.succ(200,"修改个人信息成功",null);
    }

    @GetMapping("/getinfo")
    @ResponseBody
    public Result getinfo(@RequestParam Long id){
        User user = userinfoService.getinfo(id);
        if (user != null){
            return Result.succ(200,"获取个人信息成功",user);
        }
        else{
            return Result.fail("没有此用户");
        }

    }

}
