package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by qlp on 14-4-2.
 */
public interface UserDao extends QlpJpaRepository<User, String> {

    /**
     * 根据登录名和密码查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    public User findByLoginNameAndPassword(String loginName, String password);
}
