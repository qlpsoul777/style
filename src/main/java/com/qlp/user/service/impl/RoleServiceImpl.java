package com.qlp.user.service.impl;

import com.qlp.user.dao.RoleDao;
import com.qlp.user.entity.Role;
import com.qlp.user.service.RoleService;
import com.qlp.utils.ParameterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
@Component
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * 查询所有正在使用的角色
     *
     * @return
     */
    public List<Role> findAllRoles() {
        String state = ParameterUtils.ENABLE;
        return roleDao.findByState(state);
    }
}
