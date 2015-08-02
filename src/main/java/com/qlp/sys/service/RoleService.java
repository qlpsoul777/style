package com.qlp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	/**
	 * 分页查询角色列表
	 * @param map
	 * @param pageable
	 * @return
	 */
	Page<Role> findPageByMap(Map<String, Object> map, Pageable pageable);

	void setModules(Role role, String moduleIds);

	Role save(Role role);

	boolean repeatLoginName(String name);

	void batchDelete(String roleIds);

	void batchEdit(String roleIds);

	Role findById(String id);

}
