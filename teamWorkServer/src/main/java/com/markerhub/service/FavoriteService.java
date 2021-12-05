package com.markerhub.service;

import com.markerhub.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    void addfavorite(Favorite favorite);
    void delfavorite(Favorite favorite);
    List<Favorite> getfavorite(int id);
}
