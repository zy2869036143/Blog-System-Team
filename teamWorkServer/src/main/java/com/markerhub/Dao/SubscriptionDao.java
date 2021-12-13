package com.markerhub.Dao;

import com.markerhub.entity.Subscription;
import com.markerhub.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubscriptionDao {
    void addsubscribe(Subscription subscription);
    void delsubscribe(Subscription subscription);
    List<User> getalluser();
    List<User> getsubscribe(@Param("idlist") List<Subscription> idlist);
    List<Subscription> getsid(int id);
    List<Subscription> getALLsubscribe(User user);
    List<User> getusersubedlist(@Param("sublist") List<Subscription> sublist);
}
