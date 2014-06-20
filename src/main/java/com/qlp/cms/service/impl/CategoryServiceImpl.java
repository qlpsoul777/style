package com.qlp.cms.service.impl;

import com.qlp.cms.dao.CategoryDao;
import com.qlp.cms.entity.Category;
import com.qlp.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qlp on 2014/6/20.
 */
@Component
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category get(String id) {
        return categoryDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public Category save(Category category) {
        return categoryDao.saveAndFlush(category);
    }
}
