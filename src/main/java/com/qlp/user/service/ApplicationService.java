package com.qlp.user.service;

import com.qlp.user.entity.Application;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface ApplicationService {

    List<Application> findApplicationByUser(String userId);
}
