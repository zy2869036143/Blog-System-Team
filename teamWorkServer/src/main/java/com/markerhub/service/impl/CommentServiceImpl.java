package com.markerhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.Dao.BlogDao;
import com.markerhub.Dao.CommentDao;
import com.markerhub.entity.Blog;
import com.markerhub.entity.Comment;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.mapper.CommentMapper;
import com.markerhub.service.BlogService;
import com.markerhub.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CommentService")
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> getByCommentById(Long blogId, Long follow) {
        return commentDao.getCommentByBlogId(blogId, follow);
    }
}
