package com.qlp.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.sys.entity.Module;

/**
 * Created by Administrator on 2015/5/1.
 */
public interface ModuleDao extends QlpJpaRepository<Module,String> {

	@Query("select m from Module m where m.display=1 and m.enable=1 and m.level=1 order by m.sort desc")
	List<Module> findAllEnableModule();
	
	@Query("select m from Module m where m.level=1 order by m.sort desc")
	List<Module> findAllTopModules();

	@Query("select m from Module m join m.roles r join r.members u where m.display=1 and m.enable=1 and m.level=1 and r.enable=1 and u.loginName=?1 order by m.sort desc")
	List<Module> findByLoginName(String loginName);

	@Query("select m.id as id,m.parent.id as pid,m.name as name from Module m where m.display=1 and m.enable=1 order by m.sort desc,m.level asc")
	List<Object[]> findAllUsingModule();

	List<Module> findByIdIn(Long[] id);

}
