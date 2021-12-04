package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Praise;
import com.markerhub.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/praise")
public class PraiseController {
    @Autowired
    PraiseService praiseService;

    @PostMapping("/addpraiseinfo")
    public Result addpraiseinfo(@RequestBody Praise praise){
        praise.setP_time(LocalDateTime.now());
        praiseService.addpraise(praise);
        return Result.succ("添加博客点赞信息成功");

    }

    @PostMapping("/delpraiseinfo")
    public Result delpraiseinfo(@RequestBody Praise praise){
        praiseService.delpraise(praise);
        return Result.succ("删除博客点赞信息成功");

    }

    @PostMapping("/getpraiseinfo")
    public Result getpraiseinfo(int id){
        List<Praise> praiseList = praiseService.getpraise(id);
        return Result.succ(200,"获取用户："+id+"博客点赞信息成功",praiseList);

    }

}
