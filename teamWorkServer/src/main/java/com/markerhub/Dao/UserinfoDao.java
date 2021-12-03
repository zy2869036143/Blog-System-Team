package com.markerhub.Dao;

import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserinfoDao {
    void renewinfo(User user);
    User getinfo(Long id);
    List<User> getSimilarUser(String username);
}
