package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.Application;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface ApplicationDao extends QlpJpaRepository<Application,String> {

    @Query("select distinct a from Application a join a.roles r join r.members u"
            + " where r.state = 'ENABLE' and u.id = ?1 order by a.sort")
    public List<Application> findApplicationByUser(String userId);
}
