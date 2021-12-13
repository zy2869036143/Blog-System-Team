package com.markerhub.Dao;

import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionDao {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<User> getsubscribe(int id);
    List<User> getALLsubscribe();
}
