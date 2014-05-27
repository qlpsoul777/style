package com.qlp.user.service.impl;

import com.qlp.user.dao.ApplicationDao;
import com.qlp.user.entity.Application;
import com.qlp.user.service.ApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
@Component
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {
    private static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);
    private ApplicationDao applicationDao;

    @Autowired
    public void setApplicationDao(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    /**
     * 根据用户id查询用户拥有的模块权限
     *
     * @param userId
     * @return
     */
    public List<Application> findApplicationByUser(String userId) {
        List<Application> apps = null;
        if (StringUtils.isNotBlank(userId)) {
            apps = applicationDao.findApplicationByUser(userId);
        } else {
            logger.debug("List<Application> findApplicationByUser(String userId)方法传入的参数为blank");
        }
        return apps;
    }
}
