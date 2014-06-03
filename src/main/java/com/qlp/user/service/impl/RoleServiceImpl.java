package com.qlp.user.service.impl;

import com.qlp.user.dao.RoleDao;
import com.qlp.user.entity.Role;
import com.qlp.user.service.RoleService;
import com.qlp.utils.ParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
     * @param type
     * @return
     */
    public List<Role> findAllRoles(String type) {
        String state = ParameterUtils.ENABLE;
        return roleDao.findByStateAndType(state, type);
    }

    /**
     * 根据角色id获取角色
     *
     * @param id
     * @return
     */
    public Role get(String id) {
        return roleDao.findOne(id);
    }

    public Page<Role> findPageByMap(Map<String, Object> map, Pageable pageable) {
        return roleDao.queryPageByMap(map, pageable);
    }

    /**
     * 批量启用/禁用角色
     *
     * @param ids
     */
    @Transactional(readOnly = false)
    public void batchUse(String ids) {
        String[] roleIds = StringUtils.split(ids, ',');
        for (String id : roleIds) {
            Role role = get(id);
            if (StringUtils.equals(role.getState(), ParameterUtils.ENABLE)) {
                role.setState(ParameterUtils.DISENABLE);
            } else {
                role.setState(ParameterUtils.ENABLE);
            }
            save(role);
        }
    }

    @Transactional(readOnly = false)
    public void save(Role role) {
        roleDao.saveAndFlush(role);
    }
}
