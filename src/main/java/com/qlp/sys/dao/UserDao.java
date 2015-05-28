package com.qlp.sys.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.sys.entity.User;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by qlp on 14-4-2.
 */
public interface UserDao extends QlpJpaRepository<User, String> {

    @Query(value = "select u from User u where u.loginName = ?1 or u.email = ?1")
    public User findOneByLoginNameOrEmail(String loginName);
}
