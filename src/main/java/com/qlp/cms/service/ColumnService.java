package com.qlp.cms.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.cms.dao.ColumnDao;
import com.qlp.cms.entity.Column;

/**
*Created by July At 2015-10-25
*
*/
@Component
@Transactional(readOnly = true)
public class ColumnService{

	private static Logger logger = Logger.getLogger(Column.class);
	
	@Autowired
	private ColumnDao columnDao;
	
	public Column get(String id) {
        return columnDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Column save(Column column) {
        return columnDao.saveAndFlush(column);
    }

    @Transactional(readOnly = false)
    public void delete(Column column) {
        columnDao.delete(column);
        columnDao.flush();
    }

    public Page<Column> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return columnDao.queryPageByMap(map,pageable);
    }


}
