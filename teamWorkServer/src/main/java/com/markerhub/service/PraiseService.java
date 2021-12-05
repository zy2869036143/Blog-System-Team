package com.markerhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.entity.Comment;
import com.markerhub.entity.Praise;

import java.util.List;


public interface PraiseService{
    void addpraise(Praise praise);
    void delpraise(Praise praise);
    List<Praise> getpraise(int userid);
}
