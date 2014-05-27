package com.qlp.user.service;

import com.qlp.user.entity.Functions;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface FunctionService {

    List<Functions> findFunctionsByUser(String userId);
}
