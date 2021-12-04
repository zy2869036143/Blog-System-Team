package com.markerhub.service.impl;

import com.markerhub.Dao.BlogDao;
import com.markerhub.entity.Blog;
import com.markerhub.mapper.BlogMapper;
import com.markerhub.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service("BlogService")
@MapperScan("com.markerhub.Dao")
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogDao blogDao;

    @Override
    public List<Blog> getblogByKey(String key){
        return blogDao.getblogByKey(key);
    }

    @Override
    public List<Blog> getblogs(){
        return blogDao.getblogs();
    }

    @Override
    public void updateblog(Blog blog){
        blogDao.updateblog(blog);
    }

    @Override
    public List<Blog> getByid(int id){
        return blogDao.getByid(id);
    }
}
