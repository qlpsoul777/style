package com.qlp.commons.orm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by qlp on 14-4-2.
 *
 */
@NoRepositoryBean
public abstract interface QlpJpaRepository<T,ID extends Serializable> extends JpaRepository<T,ID>{
    public abstract List<T> queryByMap(Map<String, Object> map);

    public abstract Page<T> queryPageByMap(Map<String,Object> map,Pageable pageable);
}
