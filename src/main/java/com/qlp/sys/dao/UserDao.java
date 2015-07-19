package com.qlp.sys.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.sys.entity.User;


/**
 * Created by qlp on 14-4-2.
 */
public interface UserDao extends QlpJpaRepository<User, String> {

    public User findByLoginName(String loginName);
}
