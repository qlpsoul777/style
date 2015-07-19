package com.qlp.sys.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qlp.sys.dao.RoleDao;
import com.qlp.sys.entity.Role;
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
	
	public List<Role> queryAllUsingRoles() {
		return roleDao.findAllUsingRoles();
	}

	public List<Role> findByIds(String roleIds) {
		List<Role> roles = null;
		if(StringUtils.isNotBlank(roleIds)){
			String[] ids = StringUtils.split(roleIds, ",");
			roles = (List<Role>) roleDao.findAll(Arrays.asList(ids));
		}else{
			logger.debug("%s:"+ "未选择任何角色");
		}
		return roles;
	}


}
