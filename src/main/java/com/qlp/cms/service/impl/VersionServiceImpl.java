package com.qlp.cms.service.impl;

import com.qlp.cms.dao.VersionDao;
import com.qlp.cms.entity.Version;
import com.qlp.cms.service.VersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * Created by qlp on 2014/7/20.
 */
@Component
@Transactional(readOnly = true)
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionDao versionDao;

    public Version get(String id) {
        return versionDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        Version version = versionDao.findOne(id);
        if(StringUtils.isNotBlank(version.getPath())){
            File file = new File(version.getPath());
            if(file.isFile()){
                file.delete();
            }
        }
        versionDao.delete(id);
        versionDao.flush();
    }

    @Transactional(readOnly = false)
    public Version save(Version version) {
        return versionDao.saveAndFlush(version);
    }
}
