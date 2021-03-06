package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.SiteDao;
import com.qlp.cms.entity.Site;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class SiteService{

	private static Logger logger = Logger.getLogger(Site.class);
	
	@Autowired
	private SiteDao siteDao;
	
	public Site get(String id) {
        return siteDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Site save(Site site) {
        return siteDao.saveAndFlush(site);
    }

    @Transactional(readOnly = false)
    public void delete(Site site) {
        siteDao.delete(site);
        siteDao.flush();
    }

    public Page<Site> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return siteDao.queryPageByMap(map,pageable);
    }


}
