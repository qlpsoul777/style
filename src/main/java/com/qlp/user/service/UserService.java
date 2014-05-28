package com.qlp.user.service;

import com.qlp.user.entity.User;
import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 14-4-3.
 */
public interface UserService {

    User get(String id);

    List<User> findByMap(Map<String, Object> map);

    User save(User user);

    /**
     * 根据登录名和密码查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    User findByLoginNameAndPassword(String loginName, String password);

    Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable);
}
