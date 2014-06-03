package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface RoleDao extends QlpJpaRepository<Role, String> {

    List<Role> findByStateAndType(String state, String type);
}
