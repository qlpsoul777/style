package com.qlp.cms.service;

import com.qlp.cms.entity.Version;

/**
 * Created by qlp on 2014/7/20.
 */
public interface VersionService {

    Version get(String id);

    void delete(String id);

    Version save(Version version);
}
