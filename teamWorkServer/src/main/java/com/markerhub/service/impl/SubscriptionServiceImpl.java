package com.markerhub.service.impl;

import com.markerhub.Dao.SubscriptionDao;
import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import com.markerhub.service.SubscriptionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SubscriptionService")
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionDao subscriptionDao;

    @Override
    public void addsubscribe(Subscription subscription){
        subscriptionDao.addsubscribe(subscription);
    }

    @Override
    public void delsubscribe(Subscription subscription){
        subscriptionDao.delsubscribe(subscription);
    }

    @Override
    public List<User> getsubscribe(List<Subscription> sid){
        return subscriptionDao.getsubscribe(sid);
    }

    @Override
    public List<Subscription> getALLsubscribe(User user){
        return subscriptionDao.getALLsubscribe(user);
    }

    @Override
    public List<Subscription> getsid(int id){
        return subscriptionDao.getsid(id);
    }

    @Override
    public List<User> getalluser(){
        return subscriptionDao.getalluser();
    }

    @Override
    public List<User> getusersubedlist(List<Subscription> sublist){
        return subscriptionDao.getusersubedlist(sublist);
    }
}
