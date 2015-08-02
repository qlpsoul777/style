package com.qlp.sys.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.sys.dao.RoleDao;
import com.qlp.sys.entity.Module;
import com.qlp.sys.entity.Role;
import com.qlp.sys.service.ModuleService;
import com.qlp.sys.service.RoleService;


/**
 * Created by qlp on 14-5-22.
 */
@Component
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	private static Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
    private ModuleService moduleService;
	
	public List<Role> queryAllUsingRoles() {
		return roleDao.findAllUsingRoles();
	}

	public List<Role> findByIds(String roleIds) {
		List<Role> roles = null;
		if(StringUtils.isNotBlank(roleIds)){
			String[] ids = StringUtils.split(roleIds, ",");
			roles = (List<Role>) roleDao.findAll(Arrays.asList(ids));
		}else{
			logger.info("%s:"+ "未选择任何角色");
		}
		return roles;
	}

	public Page<Role> findPageByMap(Map<String, Object> map, Pageable pageable) {
		return roleDao.queryPageByMap(map, pageable);
	}

	public void setModules(Role role, String moduleIds) {
		List<Module> modules = moduleService.findByIds(moduleIds);
		role.setModules(modules);
	}

	@Transactional(readOnly=false)
	public Role save(Role role) {
		return roleDao.saveAndFlush(role);
	}

	public boolean repeatLoginName(String name) {
		Role role = roleDao.findByName(name);
		if(role != null){
			return false;
		}
		return true;
	}

	@Transactional(readOnly=false)
	public void batchDelete(String roleIds) {
		List<Role> roles = findByIds(roleIds);
		if(roles != null && !roles.isEmpty()){
			roleDao.delete(roles);
		}
	}

	@Transactional(readOnly=false)
	public void batchEdit(String roleIds) {
		List<Role> roles = findByIds(roleIds);
		if(roles != null && !roles.isEmpty()){
			for (Role role : roles) {
				if(role.isEnable()){
					role.setEnable(false);
				}else{
					role.setEnable(true);
				}
				roleDao.saveAndFlush(role);
			}
		}
	}

	public Role findById(String id) {
		return roleDao.findOne(id);
	}


}
