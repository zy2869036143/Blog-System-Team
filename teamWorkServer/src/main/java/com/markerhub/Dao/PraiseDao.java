package com.markerhub.Dao;

import com.markerhub.entity.Praise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface PraiseDao {
    void addpraise(Praise praise);
    void delpraise(Praise praise);
    List<Praise> getpraise(int userid);
}
