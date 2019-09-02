package com.yibi.core.service.impl;

import com.yibi.core.dao.DocMapper;
import com.yibi.core.dao.NewsMapper;
import com.yibi.core.entity.Doc;
import com.yibi.core.entity.News;
import com.yibi.core.service.DocService;
import com.yibi.core.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:文档 doc
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("docService")
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);


    @Override
    public News selectByPrimaryKey(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<News> selectAll(Map<Object, Object> param) {
        return newsMapper.selectAll(param);
    }

    @Override
    public List<News> selectPaging(Map<Object, Object> param) {
        return newsMapper.selectPaging(param);
    }
}