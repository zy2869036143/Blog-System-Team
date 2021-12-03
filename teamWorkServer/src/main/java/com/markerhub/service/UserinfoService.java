package com.markerhub.service;

import com.markerhub.entity.User;

import java.util.List;

public interface UserinfoService {
    void renewinfo(User user);
    User getinfo(Long id);
    List<User> getSimilar(String username);
}
