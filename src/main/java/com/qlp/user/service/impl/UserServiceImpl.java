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
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public User get(String id) {
        return userDao.findOne(id);
    }

    public List<User> findByMap(Map<String, Object> map) {
        return userDao.queryByMap(map);
    }

    @Transactional(readOnly = false)
    public User save(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public User findByLoginNameAndPassword(String loginName, String password) {
        return userDao.findByLoginNameAndPassword(loginName,password);
    }
}
