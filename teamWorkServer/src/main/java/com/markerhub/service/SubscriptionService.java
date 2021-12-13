package com.markerhub.service;

import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscriptionService {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<User> getsubscribe(List<Subscription> sid);
    List<Subscription> getsid(int id);
    List<User> getalluser();
    List<Subscription> getALLsubscribe(User user);
    List<User> getusersubedlist(List<Subscription> sublist);
}
