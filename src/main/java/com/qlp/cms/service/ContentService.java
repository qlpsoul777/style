package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.ContentDao;
import com.qlp.cms.entity.Content;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class ContentService{

	private static Logger logger = Logger.getLogger(Content.class);
	
	@Autowired
	private ContentDao contentDao;
	
	public Content get(String id) {
        return contentDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Content save(Content content) {
        return contentDao.saveAndFlush(content);
    }

    @Transactional(readOnly = false)
    public void delete(Content content) {
        contentDao.delete(content);
        contentDao.flush();
    }

    public Page<Content> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return contentDao.queryPageByMap(map,pageable);
    }


}
