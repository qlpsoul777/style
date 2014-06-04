package com.qlp.user.service.impl;

import com.qlp.user.dao.UserDao;
import com.qlp.user.entity.User;
import com.qlp.user.service.UserService;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    public Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable) {
        Map<String, Object> searchMap = new HashMap<String, Object>();
        String userName = (String) map.get("userName");
        String roleName = (String) map.get("roleName");
        String type = (String) map.get("type");
        if (StringUtils.isNotBlank(userName)) {
            searchMap.put("name_li", userName);
        }
        searchMap.put("type", type);
        Criteria criteria = userDao.mapToCriteria(searchMap);
        if (StringUtils.isNotBlank(roleName)) {
            criteria.createAlias("roles", "r")
                    .add(Restrictions.eq("r.name", roleName))
                    .add(Restrictions.eq("r.state", ParameterUtils.ENABLE));
        }
        int pageSize = pageable.getPageSize();
        int pageNum = pageable.getPageNumber();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(pageNum, pageSize, sort);
        return userDao.queryPageByCriteria(criteria, pageRequest);
    }

    /**
     * 批量启用/禁用用户
     *
     * @param ids
     */
    @Transactional(readOnly = false)
    public void batchUse(String ids) {
        String[] userIds = StringUtils.split(ids, ',');
        for (String id : userIds) {
            User user = get(id);
            if (StringUtils.equals(user.getState(), ParameterUtils.ENABLE)) {
                user.setState(ParameterUtils.DISENABLE);
            } else {
                user.setState(ParameterUtils.ENABLE);
            }
            save(user);
        }
    }

    /**
     * 批量删除用户
     *
     * @param ids
     */
    @Transactional(readOnly = false)
    public void batchDelete(String ids) {
        String[] userIds = StringUtils.split(ids, ',');
        for (String id : userIds) {
            delete(id);
        }
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        userDao.delete(id);
        userDao.flush();
    }

}
