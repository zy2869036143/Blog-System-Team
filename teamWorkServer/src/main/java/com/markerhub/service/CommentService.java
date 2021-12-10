package com.markerhub.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.Comment;
import java.io.Serializable;
import java.util.List;

public interface CommentService extends IService<Comment> {
    @Override
    Comment getById(Serializable id);
    List<Comment> getByCommentById(Long blogId, Long follow);
}
