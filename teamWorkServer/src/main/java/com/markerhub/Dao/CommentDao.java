package com.markerhub.Dao;

import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    List<Comment> getCommentByBlogId(Long blogId, Long follow);
    //List<Blog> getblogs();
}
