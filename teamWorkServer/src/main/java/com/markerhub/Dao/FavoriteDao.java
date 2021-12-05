package com.markerhub.Dao;

import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteDao {
    void addfavorite(Favorite favorite);
    void delfavorite(Favorite favorite);
    List<Favorite> getfavorite(int id);
}
