package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import com.markerhub.service.BlogService;
import com.markerhub.util.ShiroUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping("/blogs")
    public Result list(@RequestBody String data) {

        JSONObject jsonObject = JSONObject.fromObject(data);
        Integer currentPage = jsonObject.getInt("currentPage");
        Page page = new Page(currentPage, 6);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

//    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {

        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            if (temp.getUserId()!=blog.getUserId()){
                return Result.fail("Can only edit your own blog!");
            }
        } else {
            temp = new Blog();
            temp.setUserId(blog.getUserId());
        }
        temp.setContent(blog.getContent());
        temp.setDescription(blog.getDescription());
        temp.setTitle(blog.getTitle());
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(0);

        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    @PostMapping("/blog/selectByKey")
    public Result getblogByKey(@RequestBody String key){
        List<Blog> blogs = blogService.getblogs();
        List<Blog> filter = blogs.stream().filter(item ->{
            return item.getContent().contains(key);
        }).collect(Collectors.toList());
        if (filter.size() == 0){
            return Result.fail("无相关博客");
        }
        else {
            return Result.succ(filter);
        }




    }

    @PostMapping("/blog/addpraisenum")
    public Result addpraisenum(@Validated @RequestBody Blog blog){
       int praise = blog.getPraise();
       blog.setPraise(++praise);
       blogService.saveOrUpdate(blog);
       return Result.succ("添加博客点赞数成功");

    }

    @PostMapping("/blog/delpraisenum")
    public Result delpraisenum(@RequestBody Blog blog){
        int praise = blog.getPraise();
        praise--;
        blog.setPraise(praise);
        blogService.saveOrUpdate(blog);
        return Result.succ("减少博客点赞数成功");

    }



    @PostMapping("/blog/addfavoritenum")
    public Result addfavoritenum(@RequestBody Blog blog){
        int favorite = blog.getFavorite();
        blog.setFavorite(++favorite);
        blogService.saveOrUpdate(blog);
        return Result.succ("添加博客收藏数成功");

    }

    @PostMapping("/blog/delfavoritenum")
    public Result delfavoritenum(@RequestBody Blog blog){
        int favorite = blog.getFavorite();
        favorite--;
        blog.setFavorite(favorite);
        blogService.saveOrUpdate(blog);
        return Result.succ("减少博客收藏数成功");

    }

    @PostMapping("/blog/getuserfavoriteblog")
    public Result getuserfavorites(int userid){
        List<Favorite> favoriteList = blogService.getfavoriteinfoByid(userid);
        List<Blog> fblogs = blogService.getfavoriteByid(favoriteList);
        if (fblogs.size()==0){
            return Result.succ("此用户无收藏博客");
        }else {
            return Result.succ(200,"获取用户收藏博客成功",fblogs);
        }


    }

    @PostMapping("/blog/getuserpraiseblog")
    public Result getuserpraises(int userid){
        List<Praise> praiseList = blogService.getpraiseinfoByid(userid);
        List<Blog> pblogs = blogService.getpraiseByid(praiseList);
        if (pblogs.size()==0){
            return Result.succ("此用户无点赞博客");
        }else {
            return Result.succ(200,"获取用户点赞博客成功",pblogs);
        }
    }


}
