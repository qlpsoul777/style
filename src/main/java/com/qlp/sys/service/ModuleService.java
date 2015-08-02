package com.qlp.sys.service;

import java.util.List;

import com.qlp.sys.dto._Module;
import com.qlp.sys.entity.Module;

public interface ModuleService {

	/**
	 * 根据登录名查询用户权限菜单
	 * @param loginName
	 * @return
	 */
	List<Module> queryMenuByLoginName(String loginName);

	/**
	 * 查询所有正在使用的权限
	 * @return
	 */
	List<_Module> findAllUsingModule();

	/**
	 * 根据权限id集合查询权限集合
	 * @param moduleIds
	 * @return
	 */
	List<Module> findByIds(String moduleIds);

	/**
	 * 查询所有权限json对象
	 * @return
	 */
	String findAllModules();

}
