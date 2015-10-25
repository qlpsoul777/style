package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.AdvertisementDao;
import com.qlp.cms.entity.Advertisement;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class AdvertisementService{

	private static Logger logger = Logger.getLogger(Advertisement.class);
	
	@Autowired
	private AdvertisementDao advertisementDao;
	
	public Advertisement get(String id) {
        return advertisementDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Advertisement save(Advertisement advertisement) {
        return advertisementDao.saveAndFlush(advertisement);
    }

    @Transactional(readOnly = false)
    public void delete(Advertisement advertisement) {
        advertisementDao.delete(advertisement);
        advertisementDao.flush();
    }

    public Page<Advertisement> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return advertisementDao.queryPageByMap(map,pageable);
    }


}
