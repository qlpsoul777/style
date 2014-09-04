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
     * 新增用户
     * @param user
     * @return
     */
    User createUser(User user);

    /**
     * 修改用户基本信息
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 修改用户的密码
     * @param userId
     * @param password
     * @return
     */
    User updateUserPassword(String userId,String password);

    /**
     * 根据登录名和密码查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    User findByLoginNameAndPassword(String loginName, String password);

    Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable);

    /**
     * 批量启用/禁用用户
     *
     * @param ids
     */
    void batchUse(String ids);

    /**
     * 批量删除用户
     *
     * @param ids
     */
    void batchDelete(String ids);

    void delete(String id);

    List<User> findByRoleId(String roleId);
}
