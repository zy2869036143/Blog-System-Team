package com.markerhub.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.LoginDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

    @PostMapping("/editPassword")
    public Result editPassword(@Validated @RequestBody LoginDto editPassword){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", editPassword.getUsername()));
        try {
            Assert.notNull(user, "用户不存在");
        }catch (IllegalArgumentException e){}
        String secure = SecureUtil.md5(editPassword.getPassword());
        if (user.getPassword().equals(secure)){
            System.out.println("修改密码成功");
            user.setPassword(SecureUtil.md5(editPassword.getNewpassword()));
            userService.saveOrUpdate(user);
            return Result.succ(null);
        }
        return Result.fail("Password wrong");
    }




}
