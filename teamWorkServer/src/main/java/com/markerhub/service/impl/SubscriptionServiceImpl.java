package com.markerhub.service.impl;

import com.markerhub.Dao.SubscriptionDao;
import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import com.markerhub.service.SubscriptionService;
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
    public List<User> getsubscribe(int id){
        return subscriptionDao.getsubscribe(id);
    }
}
