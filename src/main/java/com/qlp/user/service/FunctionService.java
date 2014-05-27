package com.qlp.user.service;

import com.qlp.user.entity.Functions;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface FunctionService {

    /**
     * 根据用户id查询用户拥有的功能权限
     *
     * @param userId
     * @return
     */
    List<Functions> findFunctionsByUser(String userId);
}
