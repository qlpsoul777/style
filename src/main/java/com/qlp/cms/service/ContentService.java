package com.qlp.cms.service;

import com.qlp.cms.entity.Content;

/**
 * Created by admin on 2014/8/19.
 */
public interface ContentService {

    Content get(String id);

    Content save(Content content);
}
