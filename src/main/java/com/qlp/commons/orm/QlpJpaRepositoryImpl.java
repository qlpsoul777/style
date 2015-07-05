package com.qlp.commons.orm;


import com.qlp.utils.ReflectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
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
 */
@NoRepositoryBean
public class QlpJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements QlpJpaRepository<T, ID> {

    private final EntityManager em;
    private Class<T> entityClass;

    QlpJpaRepositoryImpl(Class<T> entityClass, EntityManager em) {
        super(entityClass, em);
        this.em = em;
        this.entityClass = entityClass;
    }

    @SuppressWarnings("unchecked")
	public List<T> queryByMap(Map<String, Object> map) {
        return mapToCriteria(map).list();
    }

    @SuppressWarnings("unchecked")
	public List<T> queryByMap(Map<String, Object> map, Sort sort) {
        Criteria criteria = mapToCriteria(map);
        createCriteria(criteria, sort);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
	public List<T> queryByCriteria(Criteria criteria) {
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
	public List<T> queryByCriteria(Criteria criteria, Sort sort) {
        createCriteria(criteria, sort);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Page<T> queryPageByMap(Map<String, Object> map, Pageable pageable) {
        Criteria c = mapToCriteria(map);
        long total = countCriteriaList(c);
        c.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Sort sort = pageable.getSort();
        createCriteria(c, sort);
		Page<T> page = new PageImpl<T>(c.list(), pageable, total);
        return page;
    }

    @SuppressWarnings("unchecked")
    public Page<T> queryPageByCriteria(Criteria criteria, Pageable pageable) {
        long total = countCriteriaList(criteria);
        criteria.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        Sort sort = pageable.getSort();
        createCriteria(criteria, sort);
		Page<T> page = new PageImpl<T>(criteria.list(), pageable, total);
        return page;
    }

    public Criteria mapToCriteria(Map<String, Object> map) {
        Criteria c = getSession().createCriteria(this.entityClass);
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            Object object = entry.getValue();
            if ((object != null) && (StringUtils.isNotEmpty(object.toString()))) {
                Criterion criterion = entryToCriterion((String) entry.getKey(), entry.getValue());
                c.add(criterion);
            }
        }
        return c;
    }

    public Criteria createCriteria() {
        Criteria criteria = getSession().createCriteria(entityClass);
        return criteria;
    }

    private Criterion entryToCriterion(String key, Object o) {
        String[] k = StringUtils.split(key, '_');
        if (k.length < 2) {
            return Restrictions.eq(k[0], o); //无后缀
        }
        if (StringUtils.equals("eq", k[1])) {
            return Restrictions.eq(k[0], o); //相等
        }
        if (StringUtils.equals("ne", k[1])) {
            return Restrictions.ne(k[0], o);  //不相等
        }
        if (StringUtils.equals("lt", k[1])) {
            return Restrictions.lt(k[0], o);  //小于
        }
        if (StringUtils.equals("le", k[1])) {
            return Restrictions.le(k[0], o);  //小于等于
        }
        if (StringUtils.equals("gt", k[1])) {
            return Restrictions.gt(k[0], o);  //大于
        }
        if (StringUtils.equals("ge", k[1])) {
            return Restrictions.ge(k[0], o);  //大于等于
        }
        if (StringUtils.equals("li", k[1])) {
            return Restrictions.like(k[0], "%" + o + "%");  //like
        }
        if (StringUtils.equals("nl", k[1])) {
            return Restrictions.not(Restrictions.like(k[0], "%" + o + "%"));  //not like
        }
        if (StringUtils.equals("in", k[1])) {
            return Restrictions.in(k[0], (List<?>) o);  //in
        }
        if (StringUtils.equals("ni", k[1])) {
            return Restrictions.not(Restrictions.in(k[0], (List<?>) o)); //not in
        }
        return Restrictions.eq(key, o);
    }

    @SuppressWarnings("unchecked")
	private long countCriteriaList(Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer resultTransformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries = null;
        orderEntries = (List<OrderEntry>)ReflectionUtils.getFieldValue(impl, "orderEntries");
        ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<Object>());
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
        Iterator<?> it;
        if (sort != null) {
            for (it = sort.iterator(); it.hasNext(); ) {
                Sort.Order order = (Sort.Order) it.next();
                if (order.getDirection().equals(Sort.Direction.ASC)) {
                    criteria.addOrder(Order.asc(order.getProperty()));
                } else {
                    criteria.addOrder(Order.desc(order.getProperty()));
                }
            }
        }
        return criteria;
    }

    private Session getSession() {
        return (Session) this.em.getDelegate();
    }

	public Disjunction mapToDisjunction(Map<String, Object> map) {
		Disjunction disjunction = Restrictions.disjunction();
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Map.Entry<String, Object> entry : set) {
            Object object = entry.getValue();
            if ((object != null) && (StringUtils.isNotEmpty(object.toString()))) {
                Criterion criterion = entryToCriterion((String) entry.getKey(), entry.getValue());
                disjunction.add(criterion);
            }
        }
		return disjunction;
	}
}
