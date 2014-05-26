package com.qlp.commons.orm;

import com.qlp.commons.Reflection.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

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

    public List<T> queryByMap(Map<String, Object> map, Sort sort) {
        Criteria criteria = createCriteria(map);
        createCriteria(criteria, sort);
        return criteria.list();
    }

    public List<T> queryByCriteria(Criteria criteria) {
        return criteria.list();
    }

    public List<T> queryByCriteria(Criteria criteria, Sort sort) {
        createCriteria(criteria, sort);
        return criteria.list();
    }

    public Page<T> queryPageByMap(Map<String, Object> map, Pageable pageable) {
        Criteria c = getSession().createCriteria(this.entityClass);
        long total = countCriteriaList(c);
        c.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Sort sort = pageable.getSort();
        createCriteria(c, sort);
        Page page = new PageImpl(c.list(), pageable, total);
        return page;
    }

    public Page<T> queryPageByCriteria(Criteria criteria, Pageable pageable) {
        long total = countCriteriaList(criteria);
        criteria.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Sort sort = pageable.getSort();
        createCriteria(criteria, sort);
        Page page = new PageImpl(criteria.list(), pageable, total);
        return null;
    }

    private long countCriteriaList(Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer resultTransformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries = null;
        orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
        ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        //获取记录总数
        Long totalCount = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
        long total = totalCount != null ? totalCount.longValue() : 0L;
        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (resultTransformer != null) {
            c.setResultTransformer(resultTransformer);
        }
        ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        return total;
    }

    private Criteria createCriteria(Criteria criteria, Sort sort) {
        Iterator it;
        if (sort != null) {
            for (it = sort.iterator(); it.hasNext(); ) {
                Sort.Order order = (Sort.Order) sort.iterator();
                if (order.getDirection().equals(Sort.Direction.ASC)) {
                    criteria.addOrder(Order.asc(order.getProperty()));
                } else {
                    criteria.addOrder(Order.desc(order.getProperty()));
                }
            }
        }
        return criteria;
    }

    private Criteria createCriteria(Map<String, Object> map) {
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
