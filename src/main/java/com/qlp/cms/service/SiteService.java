package com.qlp.cms.service;

import com.qlp.cms.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by admin on 2014/8/1.
 */
public interface SiteService {

    Site get(String id);

    Site save(Site site);

    void delete(Site site);

    Page<Site> findPageByMap(Map<String,Object> map,Pageable pageable);
}
