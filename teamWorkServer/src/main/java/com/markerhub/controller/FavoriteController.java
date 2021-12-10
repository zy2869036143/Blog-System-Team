package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import com.markerhub.service.FavoriteService;
import com.markerhub.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/addfavoriteinfo")
    public Result addfavoriteinfo(@RequestBody Favorite favorite){
        favorite.setFtime(LocalDateTime.now());
        favoriteService.addfavorite(favorite);
        return Result.succ("添加博客收藏信息成功");

    }

    @PostMapping("/delfavoriteinfo")
    public Result delfavoriteinfo(@RequestBody Favorite favorite){
        favoriteService.delfavorite(favorite);
        return Result.succ("删除博客收藏信息成功");

    }

    @PostMapping("/getfavoriteinfo")
    public Result getfavoriteinfo(int userid){
        List<Favorite> favoriteList = favoriteService.getfavorite(userid);
        return Result.succ(200,"获取用户id："+userid+"博客收藏信息成功",favoriteList);

    }

}
