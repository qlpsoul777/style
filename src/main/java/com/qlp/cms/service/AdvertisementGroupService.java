package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.AdvertisementGroupDao;
import com.qlp.cms.entity.AdvertisementGroup;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class AdvertisementGroupService{

	private static Logger logger = Logger.getLogger(AdvertisementGroup.class);
	
	@Autowired
	private AdvertisementGroupDao advertisementGroupDao;
	
	public AdvertisementGroup get(String id) {
        return advertisementGroupDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public AdvertisementGroup save(AdvertisementGroup advertisementGroup) {
        return advertisementGroupDao.saveAndFlush(advertisementGroup);
    }

    @Transactional(readOnly = false)
    public void delete(AdvertisementGroup advertisementGroup) {
        advertisementGroupDao.delete(advertisementGroup);
        advertisementGroupDao.flush();
    }

    public Page<AdvertisementGroup> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return advertisementGroupDao.queryPageByMap(map,pageable);
    }


}
