package com.yibi.core.service.impl;

import com.yibi.core.dao.OdinReleaseRecordMapper;
import com.yibi.core.entity.OdinReleaseRecord;
import com.yibi.core.service.OdinReleaseRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-17 17:45:44
 **/ 
@Service("odinReleaseRecordService")
public class OdinReleaseRecordServiceImpl implements OdinReleaseRecordService {
    @Resource
    private OdinReleaseRecordMapper odinReleaseRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(OdinReleaseRecordServiceImpl.class);

    @Override
    public int insert(OdinReleaseRecord record) {
        return this.odinReleaseRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(OdinReleaseRecord record) {
        return this.odinReleaseRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OdinReleaseRecord record) {
        return this.odinReleaseRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OdinReleaseRecord record) {
        return this.odinReleaseRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.odinReleaseRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OdinReleaseRecord selectByPrimaryKey(Integer id) {
        return this.odinReleaseRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OdinReleaseRecord> selectAll(Map<Object, Object> param) {
        return this.odinReleaseRecordMapper.selectAll(param);
    }

    @Override
    public List<OdinReleaseRecord> selectPaging(Map<Object, Object> param) {
        return this.odinReleaseRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.odinReleaseRecordMapper.selectCount(param);
    }
}