package com.qlp.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.sys.dao.ModuleDao;
import com.qlp.sys.dto.ModuleNode;
import com.qlp.sys.dto._Module;
import com.qlp.sys.entity.Module;
import com.qlp.sys.service.ModuleService;
import com.qlp.sys.service.UserService;
import com.qlp.utils.JsonMapper;

@Component
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {
	
	private static Logger logger = Logger.getLogger(ModuleServiceImpl.class);
	
	@Autowired
	private ModuleDao moduleDao;
	
	@Autowired
	private UserService userService;

	public List<Module> queryMenuByLoginName(String loginName) {
		List<Module> menus = null;
		if(userService.isRootUser(loginName)){
			menus = moduleDao.findAllEnableModule();
		}else{
			menus = moduleDao.findByLoginName(loginName);
		}
		return menus;
	}

	public List<_Module> findAllUsingModule() {
		List<_Module> modules = new ArrayList<>();
		List<Object[]> obj = moduleDao.findAllUsingModule();
		_Module module = null;
		for (Object[] o : obj) {
			Long pid = o[1] == null?0L:(Long)o[1];
			module = new _Module((Long)o[0], pid, (String)o[2]);
			modules.add(module);
		}
		return modules;
	}

	public List<Module> findByIds(String moduleIds) {
		List<Module> modules = null;
		if(StringUtils.isNotBlank(moduleIds)){
			String[] ids = StringUtils.split(moduleIds, ",");
			Long[] id = new Long[ids.length];
			for(int i=0;i<ids.length;i++){ 
				id[i] = Long.parseLong(ids[i]);
			}
			modules = (List<Module>) moduleDao.findByIdIn(id);
		}else{
			logger.info("%s:"+ "未选择任何权限");
		}
		return modules;
	}

	public String findAllModules() {
		List<Module> modules = moduleDao.findAllTopModules();
		List<ModuleNode> nodes = new ArrayList<ModuleNode>();
		nodes.addAll(getChildren(modules));
		return new JsonMapper().toJson(nodes);
	}

	private List<ModuleNode> getChildren(List<Module> modules) {
		List<ModuleNode> nodes = new ArrayList<ModuleNode>();
		if(modules != null && !modules.isEmpty()){
			ModuleNode node = null;
			for (Module module : modules) {
				node = new ModuleNode();
				node.setId(module.getId());
				node.setName(module.getName());
				node.setPermission(module.getPermission());
				node.setUrl(module.getUrl());
				node.setEnable(module.isEnable());
				node.setSort(module.getSort());
				node.setLevel(module.getLevel());
				node.setChildren(getChildren(module.getChildren()));
				nodes.add(node);
			}
		}
		return nodes;
	}

}
