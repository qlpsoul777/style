package com.qlp.cms.dao;

import com.qlp.cms.entity.Category;
import com.qlp.commons.orm.QlpJpaRepository;

import java.util.List;

/**
 * Created by qlp on 2014/6/20.
 */
public interface CategoryDao extends QlpJpaRepository<Category, String> {

    List<Category> findBySiteIdAndParentCategoryIdIsNull(String siteId);
}