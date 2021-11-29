package com.markerhub.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.LoginDto;
import com.markerhub.common.dto.Username;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BlogService blogService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user) {
        User currentuser = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (currentuser != null) {
            return Result.fail("user name already taken!");
        }
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setStatus(0);
        userService.save(user);
        Date date = new Date();
        System.out.println(date.getTime());
        return Result.succ(MapUtil.builder()
                .put("message", "success")
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @PostMapping("/search")
    public  Result search(@Validated @RequestBody Username username){
        User user =  userService.getOne(new QueryWrapper<User>().eq("username", username.getUsername()));
        try {
            Assert.notNull(user, "用户不存在");
            List<Blog> blogs = blogService.list(new QueryWrapper<Blog>().eq("user_id",user.getId()));
            return Result.succ(blogs);
        }catch (IllegalArgumentException e){

        }
        return  Result.fail("failed");
    }
}
