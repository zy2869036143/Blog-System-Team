package com.markerhub.service;

import com.markerhub.common.dto.Label;
import com.markerhub.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface BlogService extends IService<Blog> {
    List<Blog> getblogByKey(String key);

    List<Blog> getblogs();
    void updateLabel(Label label);
    void updateblog(Blog blog);
    List<Praise> getpraiseinfoByid(int id);
    List<Favorite> getfavoriteinfoByid(int id);
    List<Blog> getpraiseByid(List<Praise> list);
    List<Blog> getfavoriteByid(List<Favorite> list);
}
