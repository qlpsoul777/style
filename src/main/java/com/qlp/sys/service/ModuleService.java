package com.qlp.sys.service;

import java.util.List;

import com.qlp.sys.entity.Module;

public interface ModuleService {

	/**
	 * 根据登录名查询用户权限菜单
	 * @param loginName
	 * @return
	 */
	List<Module> queryMenuByLoginName(String loginName);

}
