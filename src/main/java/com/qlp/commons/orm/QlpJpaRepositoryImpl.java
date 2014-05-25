package com.qlp.commons.orm;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by qlp on 14-4-2.
 *
 */
@NoRepositoryBean
public class QlpJpaRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements QlpJpaRepository<T,ID>{

    private final EntityManager em;
    private  Class<T> entityClass;

    QlpJpaRepositoryImpl(Class<T> entityClass,EntityManager em){
        super(entityClass,em);
        this.em = em;
        this.entityClass = entityClass;
    }

    public List<T> queryByMap(Map<String, Object> map) {
        return createCriteria(map).list();
    }

    public Page<T> queryPageByMap(Map<String, Object> map,Pageable pageable) {
        Criteria c = getSession().createCriteria(this.entityClass);
        long total = 5L;
        Page page = new PageImpl(c.list(),pageable,total);
        return page;
    }

    public Criteria createCriteria(Map<String,Object> map){
        Criteria c = getSession().createCriteria(this.entityClass);
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for(Map.Entry<String, Object> entry : set){
            Object o = entry.getValue();
            if((o != null) && (StringUtils.isNotEmpty(o.toString()))){
                Criterion criterion = Restrictions.eq((String)entry.getKey(),o);
                c.add(criterion);
            }
        }
        return c;
    }

    private Session getSession() {
        return (Session)this.em.getDelegate();
    }
}
