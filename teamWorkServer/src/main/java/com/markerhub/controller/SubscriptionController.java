package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import com.markerhub.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/addsubscribe")
    public Result addsubscribe(@RequestBody Subscription subscription){
        subscription.setStime(LocalDateTime.now());
        subscriptionService.addsubscribe(subscription);
        return Result.succ("添加关注信息成功");
    }

    @PostMapping("/delsubscribe")
    public Result delsubscribe(@RequestBody Subscription subscription){
        subscription.setStime(LocalDateTime.now());
        subscriptionService.delsubscribe(subscription);
        return Result.succ("删除关注信息成功");
    }

    @PostMapping("/getsubscribe")
    public Result getsubscribe(int id){
        List<User> subscriptionList = subscriptionService.getsubscribe(id);
        return Result.succ(200,"获取用户id："+id+"关注信息成功",subscriptionList);
    }

    @PostMapping("/getALLsubscribenum")
    public Result getALLsubscribenum(){
        List<User> userList = subscriptionService.getALLsubscribe();
        return Result.succ(200,"获取所有用户被关注数成功",userList);
    }
}
