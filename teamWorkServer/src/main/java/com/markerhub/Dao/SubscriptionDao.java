package com.markerhub.Dao;

import com.markerhub.entity.Subscription;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionDao {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<Subscription> getsubscribe(int id);
}
