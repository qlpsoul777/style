package com.qlp.user.service.impl;

import com.qlp.user.dao.FunctionsDao;
import com.qlp.user.entity.Functions;
import com.qlp.user.service.FunctionService;
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
public class FunctionServiceImpl implements FunctionService {
    private static Logger logger = LoggerFactory.getLogger(FunctionServiceImpl.class);
    private FunctionsDao functionsDao;

    @Autowired
    public void setFunctionsDao(FunctionsDao functionsDao) {
        this.functionsDao = functionsDao;
    }

    /**
     * 根据用户id查询用户拥有的功能权限
     *
     * @param userId
     * @return
     */
    public List<Functions> findFunctionsByUser(String userId) {
        List<Functions> funcs = null;
        if (StringUtils.isNotBlank(userId)) {
            funcs = functionsDao.findFunctionByUser(userId);
        } else {
            logger.debug("List<Functions> findFunctionsByUser(String userId)的传入参数为blank");
        }
        return funcs;
    }
}
