package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
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
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
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

    @GetMapping("/blog/selectByKey")
    public Result getblogByKey( String key){
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


}
