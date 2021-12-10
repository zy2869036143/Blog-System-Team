package com.markerhub.service.impl;

import com.markerhub.Dao.FavoriteDao;
import com.markerhub.entity.Favorite;
import com.markerhub.entity.Praise;
import com.markerhub.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("FavoriteService")
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteDao favoriteDao;

    @Override
    public void addfavorite(Favorite favorite){
        favoriteDao.addfavorite(favorite);
    }

    @Override
    public void delfavorite(Favorite favorite){
        favoriteDao.delfavorite(favorite);
    }

    @Override
    public List<Favorite> getfavorite(int id){
        return favoriteDao.getfavorite(id);
    }
}
