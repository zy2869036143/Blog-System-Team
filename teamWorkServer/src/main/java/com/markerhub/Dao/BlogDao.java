package com.markerhub.Dao;

import com.markerhub.entity.Blog;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao {
    List<Blog> getblogByKey(String key);
    List<Blog> getblogs();
    void updateblog(Blog blog);
    List<Blog> getpraiseByid(@Param("plist") List<Praise> praiseList);
    List<Blog> getfavoriteByid(@Param("flist") List<Favorite> favoriteList);
    List<Praise> getpraiseinfoByid(int id);
    List<Favorite> getfavoriteinfoByid(int id);
}
