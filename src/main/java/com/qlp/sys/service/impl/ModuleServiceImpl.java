package com.qlp.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.sys.dao.ModuleDao;
import com.qlp.sys.entity.Module;
import com.qlp.sys.service.ModuleService;
import com.qlp.sys.service.UserService;

@Component
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {
	
	@Autowired
	private ModuleDao moduleDao;
	
	@Autowired
	private UserService userService;

	@Override
	public List<Module> queryMenuByLoginName(String loginName) {
		List<Module> menus = null;
		if(userService.isRootUser(loginName)){
			menus = moduleDao.findAllEnableModule();
		}else{
			menus = moduleDao.findByLoginName(loginName);
		}
		return menus;
	}

}
