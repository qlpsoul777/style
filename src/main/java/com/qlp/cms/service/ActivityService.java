package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.ActivityDao;
import com.qlp.cms.entity.Activity;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class ActivityService{

	private static Logger logger = Logger.getLogger(Activity.class);
	
	@Autowired
	private ActivityDao activityDao;
	
	public Activity get(String id) {
        return activityDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Activity save(Activity activity) {
        return activityDao.saveAndFlush(activity);
    }

    @Transactional(readOnly = false)
    public void delete(Activity activity) {
        activityDao.delete(activity);
        activityDao.flush();
    }

    public Page<Activity> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return activityDao.queryPageByMap(map,pageable);
    }


}
