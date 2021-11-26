package com.markerhub.service;

import com.markerhub.entity.User;

public interface UserinfoService {
    void renewinfo(User user);
    User getinfo(Long id);
}
