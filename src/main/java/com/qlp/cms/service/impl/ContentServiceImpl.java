package com.qlp.cms.service.impl;

import com.qlp.cms.dao.ContentDao;
import com.qlp.cms.entity.Content;
import com.qlp.cms.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by admin on 2014/8/19.
 */
@Component
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    public Content get(String id) {
        return contentDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Content save(Content content) {
        return contentDao.saveAndFlush(content);
    }

    public Page<Content> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return contentDao.queryPageByMap(map,pageable);
    }
}
