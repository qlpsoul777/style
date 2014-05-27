package com.qlp.user.dao;

import com.qlp.commons.orm.QlpJpaRepository;
import com.qlp.user.entity.Functions;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qlp on 14-5-22.
 */
public interface FunctionsDao extends QlpJpaRepository<Functions, String> {

    @Query("")
    List<Functions> findFunctionByUser(String userId);
}
