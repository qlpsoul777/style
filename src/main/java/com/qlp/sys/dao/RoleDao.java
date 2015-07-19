package com.qlp.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.sys.entity.Role;


/**
 * Created by qlp on 14-5-22.
 */
public interface RoleDao extends QlpJpaRepository<Role, String> {

	@Query("select r from Role r where r.enable = 1")
	List<Role> findAllUsingRoles();

}
