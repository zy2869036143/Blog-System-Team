package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.dto.BlogSERequest;
import com.markerhub.common.dto.Label;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import com.markerhub.entity.User;
import com.markerhub.service.BlogService;
import com.markerhub.service.UserService;
import com.markerhub.util.ShiroUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    UserService userService;

    @PostMapping("/blogs")
    public Result list(@RequestBody String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        Integer currentPage = jsonObject.getInt("currentPage");
        Page page = new Page(currentPage, 6);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    @PostMapping("/blogs/getByClass")
    public Result test(@Validated  @RequestBody Label label){
        List<Blog> blogList =  blogService.getblogs();
        ArrayList<Blog> result = new ArrayList<>();
        for (int i=0; i< blogList.size(); ++i){
            JSONArray labelArray = JSONArray.fromObject(blogList.get(i).getLabel());
            for (int j=0 ; j < labelArray.size(); ++i){
                if (labelArray.get(j).equals(label.getLabel())){
                    result.add(blogList.get(i));
                    break;
                }
            }
        }
        // System.out.println(jsonObject.getJSONArray("label"));
        return Result.succ(result);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");
        User user = userService.getById(blog.getUserId());
        //blog.setUsername(user.getUsername());
        return Result.succ(200, user.getUsername(), blog);
    }

//    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody BlogSERequest blogSERequest) {
        Blog temp = null;
        if(blogSERequest.getId() != null) {
            temp = blogService.getById(blogSERequest.getId());
            if (temp.getUserId()!=blogSERequest.getUserId()){
                return Result.fail("Can only edit your own blog!");
            }
        } else {
            temp = new Blog();
            temp.setUserId(blogSERequest.getUserId());
        }
        temp.setContent(blogSERequest.getContent());
        temp.setDescription(blogSERequest.getDescription());
        temp.setTitle(blogSERequest.getTitle());
        temp.setCreated(LocalDateTime.now());
        temp.setStatus(0);
        JSONArray jsonArray = JSONArray.fromObject("[\"1\"]");
        temp.setLabel("blog.getLabel():" + blogSERequest.getLabel());
        temp.setLabel(blogSERequest.getLabel().toString());
        //BeanUtil.copyProperties(blogService, temp, "id", "userId", "created", "status");
        System.out.println("前一句");
        blogService.saveOrUpdate(temp);
        System.out.println("后一句");
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
