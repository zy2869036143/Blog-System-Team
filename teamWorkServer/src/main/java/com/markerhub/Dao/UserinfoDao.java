package com.markerhub.Dao;

import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserinfoDao {
    void renewinfo(User user);
    User getinfo(Long id);
}
