package com.qlp.user.service;

import com.qlp.user.entity.Role;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface RoleService {

    /**
     * 查询所有正在使用的角色
     *
     * @return
     */
    List<Role> findAllRoles();
}
