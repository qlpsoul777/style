package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.Functions;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface FunctionsDao extends QlpJpaRepository<Functions, String> {

    /**
     * 根据用户id查询用户拥有的功能权限
     *
     * @param userId
     * @return
     */
    @Query("select f from Functions f join f.application a join a.roles r"
            + " join r.members u where r.state = 'ENABLE' and u.id = ?1"
            + " order by f.sort")
    List<Functions> findFunctionByUser(String userId);
}
