package com.markerhub.service.impl;

import com.markerhub.Dao.PraiseDao;
import com.markerhub.entity.Praise;
import com.markerhub.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PraiseService")
@Transactional
public class PraiseServiceImpl implements PraiseService {
    @Autowired
    PraiseDao praiseDao;

    @Override
    public void addpraise(Praise praise){
        praiseDao.addpraise(praise);
    }

    @Override
    public void delpraise(Praise praise){
        praiseDao.delpraise(praise);
    }

    @Override
    public List<Praise> getpraise(String username){
        return praiseDao.getpraise(username);
    }

}
