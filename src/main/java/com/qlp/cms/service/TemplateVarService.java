package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.TemplateVarDao;
import com.qlp.cms.entity.TemplateVar;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class TemplateVarService{

	private static Logger logger = Logger.getLogger(TemplateVar.class);
	
	@Autowired
	private TemplateVarDao templateVarDao;
	
	public TemplateVar get(String id) {
        return templateVarDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public TemplateVar save(TemplateVar templateVar) {
        return templateVarDao.saveAndFlush(templateVar);
    }

    @Transactional(readOnly = false)
    public void delete(TemplateVar templateVar) {
        templateVarDao.delete(templateVar);
        templateVarDao.flush();
    }

    public Page<TemplateVar> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return templateVarDao.queryPageByMap(map,pageable);
    }


}
