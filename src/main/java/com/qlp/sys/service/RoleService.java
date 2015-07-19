package com.qlp.sys.service;

import java.util.List;

import com.qlp.sys.entity.Role;

/**
 * Created by qlp on 14-5-22.
 */
public interface RoleService {

	/**
	 * 查询所有可用角色
	 * @return
	 */
	List<Role> queryAllUsingRoles();
	
	/**
	 * 根据角色id集合查询角色集合
	 * @param roleIds
	 * @return
	 */
	List<Role> findByIds(String roleIds);

}
