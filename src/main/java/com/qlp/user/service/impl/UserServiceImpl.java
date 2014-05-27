package com.qlp.user.service.impl;

import com.qlp.user.dao.UserDao;
import com.qlp.user.entity.User;
import com.qlp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 14-4-3.
 */
@Component
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param id
     * @return
     */
    public User get(String id) {
        return userDao.findOne(id);
    }

    /**
     * 根据map条件查询用户列表
     *
     * @param map
     * @return
     */
    public List<User> findByMap(Map<String, Object> map) {
        return userDao.queryByMap(map);
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public User save(User user) {
        return userDao.saveAndFlush(user);
    }

    /**
     * 根据登录名和密码查询用户信息（根据实际情况可带上enable属性条件查询）
     *
     * @param loginName
     * @param password
     * @return
     */
    public User findByLoginNameAndPassword(String loginName, String password) {
        return userDao.findByLoginNameAndPassword(loginName, password);
    }
}
