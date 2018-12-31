package com.eogo.item.service.impl;

import com.eogo.egexception.EgException;
import com.eogo.egexception.status.EgExceptionStatus;
import com.eogo.item.mapper.SpecGroupMapper;
import com.eogo.item.mapper.SpecParamMapper;
import com.eogo.item.service.SpecificationService;
import com.eogo.pojo.SpecGroup;
import com.eogo.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    SpecGroupMapper specGroupMapper;
    @Autowired
    SpecParamMapper specParamMapper;
    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> speGroups = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(speGroups)){
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return speGroups;
    }
    @Override
    public List<SpecParam> querySpecParamByGid(Long groupId, Long cid, Boolean searching){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(groupId);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> results = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(results)){
            throw new EgException(EgExceptionStatus.RESOURCE_NOT_FOUND);
        }
        return  results ;
    }
}
