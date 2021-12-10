package com.markerhub.service;

import com.markerhub.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<Subscription> getsubscribe(int id);
}
