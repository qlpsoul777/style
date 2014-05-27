package com.qlp.user.service;

import com.qlp.user.entity.Application;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface ApplicationService {

    /**
     * 根据用户id查询用户拥有的模块权限
     *
     * @param userId
     * @return
     */
    List<Application> findApplicationByUser(String userId);
}
