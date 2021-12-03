package com.markerhub.service.impl;

import com.markerhub.Dao.UserinfoDao;
import com.markerhub.entity.User;
import com.markerhub.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserinfoService")
@Transactional
public class UserinfoServiceImpl implements UserinfoService {
    @Autowired
    UserinfoDao userinfoDao;

    @Override
    public void renewinfo(User user){
        userinfoDao.renewinfo(user);
    }

    @Override
    public User getinfo(Long id){
        return userinfoDao.getinfo(id);
    }

    @Override
    public List<User> getSimilar(String username) {
        return userinfoDao.getSimilarUser(username);
    }
}
