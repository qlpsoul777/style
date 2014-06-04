package com.qlp.user.service;

import com.qlp.user.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 14-5-22.
 */
public interface RoleService {

    /**
     * 查询所有正在使用的角色
     *
     * @param type
     * @return
     */
    List<Role> findAllRoles(String type);

    /**
     * 根据角色id获取角色
     *
     * @param id
     * @return
     */
    Role get(String id);

    Page<Role> findPageByMap(Map<String, Object> map, Pageable pageable);

    /**
     * 批量启用/禁用角色
     *
     * @param ids
     */
    void batchUse(String ids);

    void save(Role role);

    List<Role> findByIds(String ids);
}
