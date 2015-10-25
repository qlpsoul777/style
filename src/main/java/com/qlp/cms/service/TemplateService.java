package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.TemplateDao;
import com.qlp.cms.entity.Template;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class TemplateService{

	private static Logger logger = Logger.getLogger(Template.class);
	
	@Autowired
	private TemplateDao templateDao;
	
	public Template get(String id) {
        return templateDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Template save(Template template) {
        return templateDao.saveAndFlush(template);
    }

    @Transactional(readOnly = false)
    public void delete(Template template) {
        templateDao.delete(template);
        templateDao.flush();
    }

    public Page<Template> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return templateDao.queryPageByMap(map,pageable);
    }


}
