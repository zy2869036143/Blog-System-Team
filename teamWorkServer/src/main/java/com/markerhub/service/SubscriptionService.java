package com.markerhub.service;

import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;

import java.util.List;

public interface SubscriptionService {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<User> getsubscribe(int id);
    List<User> getALLsubscribe();
}
