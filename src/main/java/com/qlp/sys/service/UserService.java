package com.qlp.sys.service;


import com.qlp.sys.entity.User;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by qlp on 14-4-3.
 */
public interface UserService {

    /**
     * 判断登录用户是不是root用户(方便开发测试用，上线后请禁用掉root用户)
     * @param loginName
     * @return
     */
    boolean isRootUser(String loginName);

    /**
     * 通过用户名查找用户
     * @param loginName
     * @return
     */
    User findByLoginName(String loginName);

    /**
     *通过用户名查找角色集合
     * @param loginName
     * @return
     */
    Set<String> findRolesByLoginName(String loginName);

    /**
     *通过用户名查找权限集合
     * @param loginName
     * @return
     */
    Set<String> findModulesByLoginName(String loginName);
    
    /**
     * 保存用户信息
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 创建用户
     * @param user
     * @param password
     * @return
     */
    User createUser(User user,String password);

	Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable);

	boolean repeatLoginName(String loginName);

	void setRoles(User user, String roleIds);
}
