package com.markerhub.service;

import com.markerhub.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-05-25
 */
public interface BlogService extends IService<Blog> {
    List<Blog> getblogByKey(String key);
    List<Blog> getblogs();
    void updateblog(Blog blog);
    List<Blog> getByname(String username);
}
