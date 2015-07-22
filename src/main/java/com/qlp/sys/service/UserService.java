package com.qlp.sys.service;


import com.qlp.commons.entity.MsgInfo;
import com.qlp.sys.entity.User;

import java.util.List;
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

    /**
     * 分页查询用户信息
     * @param map
     * @param pageable
     * @return
     */
	Page<User> findPageByCriteria(Map<String, Object> map, Pageable pageable);

	/**
	 * 验证登录名是否重复
	 * @param loginName
	 * @return
	 */
	boolean repeatLoginName(String loginName);

	/**
	 * 设置角色
	 * @param user
	 * @param roleIds
	 */
	void setRoles(User user, String roleIds);

	/**
	 * 批量删除内部用户
	 * @param userIds
	 */
	void batchDelete(String userIds);
	
	/**
	 * 根据用户id集合查询用户
	 * @param ids
	 * @return
	 */
	List<User> findByIds(String ids);

	/**
	 * 批量删除禁用/启用用户
	 * @param userIds
	 */
	void batchEdit(String userIds);

	/**
	 * 批量重置用户密码
	 * @param userIds
	 */
	void batchResetPwd(String userIds);

	/**
	 * 根据id查询单个用户
	 * @param userIds
	 * @return
	 */
	User findById(String userIds);

	/**
	 * 修改用户密码
	 * @param user
	 * @param oldPwd
	 * @param newPwd
	 * @param repeatNewPwd
	 * @return
	 */
	MsgInfo updatePwd(User user, String oldPwd, String newPwd,
			String repeatNewPwd);
}
