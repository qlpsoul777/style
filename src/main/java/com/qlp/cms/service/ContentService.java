package com.qlp.cms.service;

import com.qlp.cms.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by admin on 2014/8/19.
 */
public interface ContentService {

    Content get(String id);

    Content save(Content content);

    Page<Content> findPageByMap(Map<String,Object> map,Pageable pageable);
}
